package com.asad.currencyapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.data.local.db.repository.CurrenciesRepository
import com.asad.currencyapp.data.remote.models.base.State
import com.asad.currencyapp.ui.currencyrate.CurrencyRateRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.withContext

@HiltWorker
class FetchCurrencyQuotesWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val rateRepository: CurrencyRateRepository,
    private val currenciesRepository: CurrenciesRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        var result = Result.failure()
        Log.d("asad_api", "Background Worker Start")
        withContext(Dispatchers.IO) {
            rateRepository.fetchCurrencyQuotes()
                .zip(rateRepository.fetchCurrencyNames()) { currencyQuotesResponse, currencyNamesResponse ->
                    when (currencyQuotesResponse) {
                        is State.Loading -> {
                            Log.d("asad_api", "Loading...")
                        }
                        is State.Success -> {
                            result = if (currencyQuotesResponse.wrapperData.quotes != null) {
                                saveCurrencyQuotesIntoDatabase(currencyQuotesResponse.wrapperData.quotes.toList())
                                Result.success()
                            } else Result.failure()
                        }
                        is State.Error -> {
                            Log.d("asad_api", "Error")
                            result = Result.failure()
                        }
                    }
                    when (currencyNamesResponse) {
                        is State.Loading -> {
                            Log.d("asad_api", "Loading...")
                        }
                        is State.Success -> {
                            result = if (currencyNamesResponse.wrapperData.currencies != null) {
                                saveCurrencyNamesIntoDatabase(currencyNamesResponse.wrapperData.currencies.toList())

                                Result.success()
                            } else
                                Result.failure()
                        }
                        is State.Error -> {
                            Log.d("asad_api", "Error")
                            result = Result.failure()
                        }
                    }
                }.collect {
                    Log.d("asad_api", "Both Apis Completed")
                    result = Result.success()
                }
        }
        return result
    }

    private fun saveCurrencyNamesIntoDatabase(currencyNameList: List<Pair<String, String>>) {
        currencyNameList.map { pair ->
            CurrencyNames(
                pair.first,
                pair.second
            )
        }.run {
            currenciesRepository.insertCurrencyNames(this)
        }
    }

    private fun saveCurrencyQuotesIntoDatabase(currencyRateList: List<Pair<String, Double>>) {
        currencyRateList.map { pair ->
            CurrencyRates(
                pair.first.substring(3, pair.first.length),
                pair.second
            )
        }.run {
            currenciesRepository.insertCurrencyQuotes(this)
        }

    }
}