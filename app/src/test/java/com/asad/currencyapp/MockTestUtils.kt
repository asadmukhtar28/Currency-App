package com.asad.currencyapp

import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.data.remote.models.currencylist.CurrencyList
import com.asad.currencyapp.data.remote.models.currencyrate.CurrencyQuotes

object MockTestUtils {
    fun fetchCurrencyNames(): CurrencyList {
        val currencies = HashMap<String, String>()
        currencies["AED"] = "United Arab Emirates Dirham"
        currencies["AFN"] = "Afghan Afghani"
        currencies["ALL"] = "Albanian Lek"

        return CurrencyList(
            currencies
        ).apply {
            privacy = "https://currencylayer.com/privacy"
            success = true
            terms = "https://currencylayer.com/terms"
        }
    }

    fun fetchCurrencyQuotes(): CurrencyQuotes {
        val quotes = HashMap<String, Double>()
        quotes["USDAED"] = 3.67299
        quotes["USDAFN"] = 104.530194
        quotes["USDALL"] = 106.622826

        return CurrencyQuotes(
            quotes,
            "USD", 1642147864
        ).apply {
            privacy = "https://currencylayer.com/privacy"
            success = true
            terms = "https://currencylayer.com/terms"
        }
    }

    fun fetchCurrencyQuoteList(): List<CurrencyRates> {
        val quotes = HashMap<String, Double>()
        quotes["USDAED"] = 3.67299
        quotes["USDAFN"] = 104.530194
        quotes["USDALL"] = 106.622826

        return ArrayList<CurrencyRates>().apply {
            add(
                CurrencyRates(
                    "USDAED",
                    3.67299
                )
            )
            add(
                CurrencyRates(
                    "USDAFN",
                    104.530194
                )
            )
            add(
                CurrencyRates(
                    "USDALL",
                    106.622826
                )
            )
        }
    }
}