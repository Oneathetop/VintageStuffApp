package com.example.tiramisuonlineshop.model

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String,
        @Query ("symbols") symbols: String
    ): ExchangeRateResponse
}