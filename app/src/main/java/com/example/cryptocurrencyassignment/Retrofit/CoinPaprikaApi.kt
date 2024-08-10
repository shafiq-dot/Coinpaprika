package com.example.cryptocurrencyassignment.Retrofit

import com.example.cryptocurrencyassignment.Model.Coin
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
    @GET("coins")
    suspend fun getCoins(): List<Coin>

    @GET("/v1/coins/{id}")
    suspend fun getCoinDetails(@Path("id") id: String): Coin
}
