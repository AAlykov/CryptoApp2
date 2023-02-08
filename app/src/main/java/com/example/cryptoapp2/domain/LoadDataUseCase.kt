package com.example.cryptoapp2.domain

class LoadDataUseCase (private val repositoryInterface: CoinRepositoryInterface) {
    suspend operator fun invoke() = repositoryInterface.loadData()
}