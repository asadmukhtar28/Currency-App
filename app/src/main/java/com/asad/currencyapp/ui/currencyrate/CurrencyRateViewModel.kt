package com.asad.currencyapp.ui.currencyrate

import androidx.lifecycle.*
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.data.local.db.repository.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyRateViewModel @Inject constructor(
    repository: CurrenciesRepository
) : ViewModel() {

    val currencyExchangeRateList: LiveData<List<CurrencyRates>> =
        repository.getAllCurrencyQuotes().asLiveData()

    val currencyExchangeRate = MutableLiveData<List<CurrencyRates>>()

    val currencyNameList: LiveData<List<CurrencyNames>> =
        repository.getAllCurrencyNames().asLiveData()

    fun exchangeCurrencyQuotes(currencyName: String, amount: Double) {
        viewModelScope.launch(Dispatchers.Default) {
            val list: MutableList<CurrencyRates> = ArrayList()
            currencyExchangeRateList.value?.let { listItems ->
                if (listItems.isNotEmpty()) {
                    val currencyValue = amount / listItems.filter {
                        currencyName == it.currencyName
                    }.map {
                        it.currencyExchangeValue
                    }.first()

                    listItems.forEach {
                        list.add(
                            CurrencyRates(
                                it.currencyName,
                                it.currencyExchangeValue * currencyValue
                            )
                        )
                    }
                    currencyExchangeRate.postValue(list)
                }
            }
        }
    }
}