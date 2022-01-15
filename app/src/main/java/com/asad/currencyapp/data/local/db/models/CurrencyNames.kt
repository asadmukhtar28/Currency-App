package com.asad.currencyapp.data.local.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asad.currencyapp.utils.TABLE_CURRENCY_NAMES

@Entity(tableName = TABLE_CURRENCY_NAMES)
data class CurrencyNames(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var currencyName: String,
    var currencyCountryName: String
) {
    constructor(name: String, countryName: String) : this(0, name, countryName)
}