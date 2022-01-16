package com.asad.currencyapp.utils

import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.data.remote.models.currencylist.CurrencyList
import com.asad.currencyapp.data.remote.models.currencyrate.CurrencyQuotes

object MockTestUtils {
    fun fetchCurrencyNames(): CurrencyList {
        val currencies: HashMap<String, String> = HashMap()
        currencies.put("AED", "United Arab Emirates Dirham")
        currencies.put("AFN", "Afghan Afghani")
        currencies.put("ALL", "Albanian Lek")

        return CurrencyList(
            currencies
        ).apply {
            privacy = "https://currencylayer.com/privacy"
            success = true
            terms = "https://currencylayer.com/terms"
        }
    }

    fun fetchCurrencyQuotes(): CurrencyQuotes {
        val quotes: HashMap<String, Double> = HashMap<String, Double>()
        quotes.put("USDAED", 3.67299)
        quotes.put("USDAFN", 104.530194)
        quotes.put("USDALL", 106.622826)

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

    fun fetchCurrencyNameList(): List<CurrencyNames> {
        return ArrayList<CurrencyNames>().apply {
            add(CurrencyNames("AED","United Arab Emirates Dirham"))
            add(CurrencyNames("AFN","Afghan Afghani"))
            add(CurrencyNames("ALL","Albanian Lek"))
        }
    }
}