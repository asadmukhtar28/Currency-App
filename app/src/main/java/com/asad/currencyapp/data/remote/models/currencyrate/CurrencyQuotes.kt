package com.asad.currencyapp.data.remote.models.currencyrate

import com.asad.currencyapp.data.remote.models.base.BaseWrapperModel
import com.google.gson.annotations.SerializedName

data class CurrencyQuotes(
    @SerializedName("quotes")
    val quotes: Map<String, Double>,
    val source: String = "",
    val timestamp: Int
) : BaseWrapperModel()