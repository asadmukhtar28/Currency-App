package com.asad.currencyapp.di.module

import com.asad.currencyapp.BuildConfig
import com.asad.currencyapp.data.remote.CurrencyLayerApiService
import com.asad.currencyapp.utils.ApiHttpClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): CurrencyLayerApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(ApiHttpClient.getHTTPClient())
            .build()
            .create(CurrencyLayerApiService::class.java)


}
