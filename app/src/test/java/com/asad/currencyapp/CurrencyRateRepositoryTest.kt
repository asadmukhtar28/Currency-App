package com.asad.currencyapp

import com.asad.currencyapp.data.remote.models.base.State
import com.asad.currencyapp.data.remote.models.currencylist.CurrencyList
import com.asad.currencyapp.data.remote.models.currencyrate.CurrencyQuotes
import com.asad.currencyapp.ui.currencyrate.CurrencyRateRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before

class CurrencyRateRepositoryTest : TestCase() {

    @MockK
    lateinit var rateRepository: CurrencyRateRepository

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    fun `test invoking fetchCurrencyNames gives an object of CurrencyList`() = runBlocking {
        val currencyNames = MockTestUtils.fetchCurrencyNames()

        coEvery {
            rateRepository.fetchCurrencyNames()
        }.returns(flowOf(State.Success(currencyNames)))

        val currencies = rateRepository.fetchCurrencyNames()
        MatcherAssert.assertThat(currencies, CoreMatchers.notNullValue())
        currencies.collect {
            when (it) {
                is State.Success -> {
                    MatcherAssert.assertThat(
                        it.wrapperData,
                        CoreMatchers.instanceOf(CurrencyList::class.java)
                    )
                    MatcherAssert.assertThat(
                        it.wrapperData.currencies["AED"],
                        CoreMatchers.equalTo("United Arab Emirates Dirham")
                    )
                }
            }
        }
    }

    fun `test invoking fetchCurrencyQuotes gives an object of CurrencyQuotes`() = runBlocking {
        val currencyQuotes = MockTestUtils.fetchCurrencyQuotes()

        coEvery {
            rateRepository.fetchCurrencyQuotes()
        }.returns(flowOf(State.Success(currencyQuotes)))

        val quotes = rateRepository.fetchCurrencyQuotes()
        MatcherAssert.assertThat(quotes, CoreMatchers.notNullValue())
        quotes.collect {
            when (it) {
                is State.Success -> {
                    MatcherAssert.assertThat(
                        it.wrapperData,
                        CoreMatchers.instanceOf(CurrencyQuotes::class.java)
                    )
                    MatcherAssert.assertThat(
                        it.wrapperData.quotes["USDAED"].toString(),
                        CoreMatchers.equalTo("3.67299")
                    )
                }
            }
        }
    }

    fun `test invoking NoInternetException`() = runBlocking {
        coEvery {
            rateRepository.fetchCurrencyQuotes()
        }.returns(flowOf(State.Error(State.ResponseError.InternetConnectionResponseError)))

        val quotes = rateRepository.fetchCurrencyQuotes()
        quotes.collect {
            when (it) {
                is State.Error -> {
                    MatcherAssert.assertThat(
                        it.responseResponseError,
                        CoreMatchers.instanceOf(State.ResponseError::class.java)
                    )
                }
            }
        }
    }

    fun `test invoking SomethingWentWrongException`() = runBlocking {
        coEvery {
            rateRepository.fetchCurrencyQuotes()
        }.returns(flowOf(State.Error(State.ResponseError.SomethingWentWrong())))

        val quotes = rateRepository.fetchCurrencyQuotes()
        quotes.collect {
            when (it) {
                is State.Error -> {
                    MatcherAssert.assertThat(
                        it.responseResponseError,
                        CoreMatchers.instanceOf(State.ResponseError::class.java)
                    )
                }
            }
        }
    }
}