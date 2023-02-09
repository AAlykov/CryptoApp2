package com.example.cryptoapp2.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.cryptoapp2.data.database.AppDatabase
import com.example.cryptoapp2.data.mapper.CoinMapper
import com.example.cryptoapp2.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(context: Context, workerParameters: WorkerParameters):CoroutineWorker(context, workerParameters) {

    private val coinInfoDao = AppDatabase.getInstance(context).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override suspend fun doWork(): Result {
        Log.d("AADebug", "doWork: Begin")
        while (true) {
            try {  //самая простая обработка ошибки при работе с корутинами
                Log.d("AADebug", "doWork: try")
                val topCoins = apiService.getTopCoinsInfo(limit = 50)  //получили топ-50
                val fromSymbols = mapper.mapNameListToString(topCoins) //преобразоваи в ожну строку
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols) //по строке загружаем данные из сети
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer) //преобразовали в коллекцию объектов dto

                val dbModelList = coinInfoDtoList.map {mapper.mapDtoToDbModel(it)} //преобазвали в коллекцию об. БД
                coinInfoDao.insertPriceList(dbModelList) //вставляем данные в БД
            } catch (e: Exception) {
                Log.d("AADebug", "doWork: Exception" + e.toString())
            }
            delay(10000)
            Log.d("AADebug", "doWork: delay 10000")
        }

    }

    companion object {
        const val NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }



}