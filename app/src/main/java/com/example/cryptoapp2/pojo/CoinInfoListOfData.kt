package com.example.cryptoapp2.pojo

import com.example.cryptoapp2.pojo.Datum
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoListOfData (
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)
