package com.example.cryptoapp2.domain

import androidx.lifecycle.LiveData

interface CoinRepositoryInterface {
    fun getCoinInfoList(): LiveData<List<CoinInfo>>
    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>
}