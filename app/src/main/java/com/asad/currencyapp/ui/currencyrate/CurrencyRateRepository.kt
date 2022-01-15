package com.asad.currencyapp.ui.currencyrate

import com.asad.currencyapp.data.remote.CurrencyLayerApiService
import com.asad.currencyapp.data.remote.models.base.State
import com.asad.currencyapp.data.remote.models.currencylist.CurrencyList
import com.asad.currencyapp.data.remote.models.currencyrate.CurrencyQuotes
import com.asad.currencyapp.ui.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CurrencyRateRepository @Inject constructor(private val apiService: CurrencyLayerApiService) :
    BaseRepository() {
    suspend fun fetchCurrencyQuotes(): Flow<State<CurrencyQuotes>> {
        return flow {
            try {
                emit(State.Loading())
                val result = makeApiCall { apiService.fetchCurrencyQuotes() }
                emit(result)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(State.Error(State.ResponseError.InternetConnectionResponseError))
            } catch (e: IllegalStateException) {
                emit(State.Error(State.ResponseError.SomethingWentWrong()))
            }
        }
    }

    suspend fun fetchCurrencyNames(): Flow<State<CurrencyList>> {
        return flow {
            try {
                emit(State.Loading())
                val result = makeApiCall { apiService.fetchCurrencyNames() }
                emit(result)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(State.Error(State.ResponseError.InternetConnectionResponseError))
            } catch (e: IllegalStateException) {
                emit(State.Error(State.ResponseError.SomethingWentWrong()))
            }

        }
    }
}