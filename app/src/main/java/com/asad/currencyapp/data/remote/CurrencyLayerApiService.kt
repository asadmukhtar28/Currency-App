package com.asad.currencyapp.data.remote

import com.asad.currencyapp.BuildConfig
import com.asad.currencyapp.data.remote.models.currencylist.CurrencyList
import com.asad.currencyapp.data.remote.models.currencyrate.CurrencyQuotes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyLayerApiService {
    @GET("live")
    suspend fun fetchCurrencyQuotes(
        @Query("access_key")
        apiKey: String = BuildConfig.API_KEY
    ): Response<CurrencyQuotes>

    @GET("list")
    suspend fun fetchCurrencyNames(
        @Query("access_key")
        apiKey: String = BuildConfig.API_KEY
    ): Response<CurrencyList>
}