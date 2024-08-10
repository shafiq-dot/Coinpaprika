package com.example.cryptocurrencyassignment.Repository

import com.example.cryptocurrencyassignment.Model.Coin
import com.example.cryptocurrencyassignment.Retrofit.CoinPaprikaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRepository(private val api: CoinPaprikaApi) {

    suspend fun fetchCoins(): List<Coin> {
        return withContext(Dispatchers.IO) {
            api.getCoins()
        }
    }

    suspend fun getCoinDetails(id: String): Coin {
        return api.getCoinDetails(id)
    }
}
