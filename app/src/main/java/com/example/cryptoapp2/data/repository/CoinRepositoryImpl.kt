package com.example.cryptoapp2.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp2.data.database.AppDatabase
import com.example.cryptoapp2.data.mapper.CoinMapper
import com.example.cryptoapp2.data.network.ApiFactory
import com.example.cryptoapp2.domain.CoinInfo
import com.example.cryptoapp2.domain.CoinRepositoryInterface
import kotlinx.coroutines.delay

class CoinRepositoryImpl(private val application: Application): CoinRepositoryInterface {

    //val application - чтобы можно было работать с БД
    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> { //траснформация используется с лайвданными
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {  //самая простая обработка ошибки при работе с корутинами
                val topCoins = apiService.getTopCoinsInfo(limit = 50)  //получили топ-50
                val fromSymbols = mapper.mapNameListToString(topCoins) //преобразоваи в ожну строку
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols) //по строке загружаем данные из сети
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer) //преобразовали в коллекцию объектов dto

                val dbModelList = coinInfoDtoList.map {mapper.mapDtoToDbModel(it)} //преобазвали в коллекцию об. БД
                coinInfoDao.insertPriceList(dbModelList) //вставляем данные в БД
            } catch (e: Exception) {

            }
            delay(5000)

        }
    }
}