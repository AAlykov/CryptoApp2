package com.example.cryptoapp2.domain

class LoadDataUseCase (private val repositoryInterface: CoinRepositoryInterface) {

    operator fun invoke() = repositoryInterface.loadData()

}