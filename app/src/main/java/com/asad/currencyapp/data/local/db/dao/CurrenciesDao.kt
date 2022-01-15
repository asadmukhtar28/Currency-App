package com.asad.currencyapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.utils.TABLE_CURRENCY_NAMES
import com.asad.currencyapp.utils.TABLE_CURRENCY_RATES
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyNames(currencyNames: List<CurrencyNames>)

    @Query("Select *FROM $TABLE_CURRENCY_NAMES")
    abstract fun getAllCurrencyNames(): Flow<List<CurrencyNames>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyQuotes(currencyQuotes: List<CurrencyRates>)

    @Query("Select *FROM $TABLE_CURRENCY_RATES")
    abstract fun getAllCurrencyQuotes(): Flow<List<CurrencyRates>>
}