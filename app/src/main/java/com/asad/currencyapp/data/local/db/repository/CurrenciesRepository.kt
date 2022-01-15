package com.asad.currencyapp.data.local.db.repository

import com.asad.currencyapp.data.local.db.dao.CurrenciesDao
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class CurrenciesRepository @Inject constructor(var currenciesDao: CurrenciesDao) {
    fun insertCurrencyNames(currencyNames: List<CurrencyNames>) {
        currenciesDao.insertCurrencyNames(currencyNames)
    }

    fun getAllCurrencyNames(): Flow<List<CurrencyNames>> {
        return currenciesDao.getAllCurrencyNames()
    }

    fun insertCurrencyQuotes(currencyQuotes: List<CurrencyRates>) {
        currenciesDao.insertCurrencyQuotes(currencyQuotes)
    }

    fun getAllCurrencyQuotes(): Flow<List<CurrencyRates>> {
        return currenciesDao.getAllCurrencyQuotes()
    }
}