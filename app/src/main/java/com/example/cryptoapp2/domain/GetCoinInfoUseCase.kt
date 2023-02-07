package com.example.cryptoapp2.domain

class GetCoinInfoUseCase (private val coinRepositoryInterface: CoinRepositoryInterface) {

    operator fun invoke(fromSymbol: String) = coinRepositoryInterface.getCoinInfo(fromSymbol)

}