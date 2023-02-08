package com.example.cryptoapp2.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp2.domain.CoinInfo

//class CoinInfoDiffCallback: DiffUtil.ItemCallback<CoinInfo>() {
object CoinInfoDiffCallback: DiffUtil.ItemCallback<CoinInfo>() { //object сделали потому что он используется один раз
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol //сравнивает один и тот же это объект?
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return  oldItem == newItem //не изменилось ли?
    }
}