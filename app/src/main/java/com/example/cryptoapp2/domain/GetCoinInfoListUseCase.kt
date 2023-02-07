package com.example.cryptoapp2.domain

class GetCoinInfoListUseCase (private val coinRepositoryInterface: CoinRepositoryInterface) {

    operator fun invoke() = coinRepositoryInterface.getCoinInfoList()
}