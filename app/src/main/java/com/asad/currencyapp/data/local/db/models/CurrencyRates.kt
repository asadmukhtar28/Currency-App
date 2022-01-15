package com.asad.currencyapp.data.local.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asad.currencyapp.utils.TABLE_CURRENCY_RATES

@Entity(tableName = TABLE_CURRENCY_RATES)
data class CurrencyRates(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var currencyName: String,
    var currencyExchangeValue: Double
) {
    constructor(name: String, value: Double) : this(0, name, value)
}