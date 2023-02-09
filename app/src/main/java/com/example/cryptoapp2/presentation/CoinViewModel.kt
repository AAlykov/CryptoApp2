package com.example.cryptoapp2.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp2.data.network.ApiFactory
import com.example.cryptoapp2.data.database.AppDatabase
import com.example.cryptoapp2.data.network.model.CoinInfoDto
import com.example.cryptoapp2.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp2.data.repository.CoinRepositoryImpl
import com.example.cryptoapp2.domain.GetCoinInfoListUseCase
import com.example.cryptoapp2.domain.GetCoinInfoUseCase
import com.example.cryptoapp2.domain.LoadDataUseCase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    //private val db = AppDatabase.getInstance(application)
    //private val compositeDisposable = CompositeDisposable()

    private val repositoryImpl = CoinRepositoryImpl(application)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repositoryImpl)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repositoryImpl)
    private val loadDataUseCase = LoadDataUseCase(repositoryImpl)



    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        //loadData()
        //viewModelScope.launch {
            loadDataUseCase()
        //}
    }

/*
    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            .map { it.names?.map { it.coinName?.name }?.joinToString(",") }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }
    */

    /*
    private fun getPriceListFromRawData(
        coinInfoJsonContainerDto: CoinInfoJsonContainerDto
    ): List<CoinInfoDto> {
        val result = ArrayList<CoinInfoDto>()
        val jsonObject = coinInfoJsonContainerDto.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
    */
}