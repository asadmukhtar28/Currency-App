package com.asad.currencyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.asad.currencyapp.data.local.db.dao.CurrenciesDao
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.data.local.db.repository.CurrenciesRepository
import com.asad.currencyapp.ui.currencyrate.CurrencyRateViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrencyRateViewModelTest : TestCase() {
    lateinit var viewModel: CurrencyRateViewModel

    @MockK
    lateinit var currenciesDao: CurrenciesDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var repository: CurrenciesRepository

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun `test viewModel is initialized and currencyExchangeRateList fetched`() = runBlocking {

        val currencyRateList = MockTestUtils.fetchCurrencyQuoteList()
        val currencyRateListObserver = mockk<Observer<List<CurrencyRates>>>(relaxed = true)
        repository.currenciesDao = currenciesDao
        // When
        coEvery { repository.getAllCurrencyQuotes() }
            .returns(flowOf(currencyRateList))

        // Invoke
        viewModel = CurrencyRateViewModel(repository)
        viewModel.currencyExchangeRateList.observeForever(currencyRateListObserver)

        // Then
        coVerify(exactly = 1) { repository.getAllCurrencyQuotes() }
        verify { currencyRateListObserver.onChanged(match { it.size == currencyRateList.size }) }
    }
}