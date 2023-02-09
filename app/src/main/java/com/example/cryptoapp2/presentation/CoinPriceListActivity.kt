package com.example.cryptoapp2.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp2.R
import com.example.cryptoapp2.databinding.ActivityCoinDetailBinding
import com.example.cryptoapp2.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp2.presentation.adapters.CoinInfoAdapter

import com.example.cryptoapp2.domain.CoinInfo


class CoinPriceListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (binding.fragmentContainer == null) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        //binding.rvCoinPriceList.itemAnimator = null //убрали анимацию в ресайклере
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this, Observer {
            //adapter.coinInfoList = it
            adapter.submitList(it)
        })
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity,fromSymbol )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}