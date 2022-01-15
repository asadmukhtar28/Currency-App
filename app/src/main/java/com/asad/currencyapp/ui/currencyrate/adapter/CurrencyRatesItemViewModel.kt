package com.asad.currencyapp.ui.currencyrate.adapter

import com.asad.currencyapp.data.local.db.models.CurrencyRates

class CurrencyRatesItemViewModel(model: CurrencyRates) {
    val nameAndValue = "${model.currencyName}  ${model.currencyExchangeValue}"
}