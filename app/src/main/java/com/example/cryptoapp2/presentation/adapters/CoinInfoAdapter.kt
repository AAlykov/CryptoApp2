package com.example.cryptoapp2.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp2.R
import com.example.cryptoapp2.databinding.ItemCoinInfoBinding
import com.example.cryptoapp2.domain.CoinInfo
import com.squareup.picasso.Picasso


//class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {
class CoinInfoAdapter(private val context: Context) : ListAdapter<CoinInfo, CoinInfoAdapter.CoinInfoViewHolder>(CoinInfoDiffCallback) {

    /*
    var coinInfoList: List<CoinInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    */

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        //return CoinInfoViewHolder(view)
        val binding = ItemCoinInfoBinding.inflate( LayoutInflater.from(parent.context), parent,false)
        return CoinInfoViewHolder(binding)
    }

    //override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        //val coin = coinInfoList[position]
        val coin = getItem(position)
        with(holder.binding) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivLogoCoin)
                //itemView.setOnClickListener {
                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    //inner
    class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        /*
        ВСЕ ДАННЫЕ ТЕПЕРЬ В Binding
        val ivLogoCoin = itemView.ivLogoCoin
        val tvSymbols = itemView.tvSymbols
        val tvPrice = itemView.tvPrice
        val tvLastUpdate = itemView.tvLastUpdate*/
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}