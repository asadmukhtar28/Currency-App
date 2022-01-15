package com.asad.currencyapp.data.remote.models.currencylist

import com.asad.currencyapp.data.remote.models.base.BaseWrapperModel
import com.google.gson.annotations.SerializedName

data class CurrencyList(
    @SerializedName("currencies")
    val currencies: Map<String, String> = HashMap()
) : BaseWrapperModel()