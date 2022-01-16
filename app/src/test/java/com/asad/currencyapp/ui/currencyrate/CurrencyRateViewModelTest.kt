package com.asad.currencyapp.ui.currencyrate

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.asad.currencyapp.MainCoroutinesRule
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.utils.MockTestUtils
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

    @MockK
    lateinit var viewModel: CurrencyRateViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @Before
    public override fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun `test currencyExchangeRateList LiveData`() = runBlocking {

        val currencyRateList = MockTestUtils.fetchCurrencyQuoteList()
        val currencyRateListObserver = mockk<Observer<List<CurrencyRates>>>(relaxed = true)

        coEvery { viewModel.currencyExchangeRateList }
            .returns(flowOf(currencyRateList).asLiveData())


        viewModel.currencyExchangeRateList.observeForever(currencyRateListObserver)

        verify {
            currencyRateListObserver.onChanged(
                match {
                    it.size == currencyRateList.size
                    it.contains(currencyRateList.first())
                })
        }
    }

    @Test
    fun `test currencyNameList LiveData`() = runBlocking {

        val currencyNameList = MockTestUtils.fetchCurrencyNameList()
        val currencyNameListObserver = mockk<Observer<List<CurrencyNames>>>(relaxed = true)

        coEvery { viewModel.currencyNameList }
            .returns(flowOf(currencyNameList).asLiveData())


        viewModel.currencyNameList.observeForever(currencyNameListObserver)

        verify {
            currencyNameListObserver.onChanged(
                match {
                    it.size == currencyNameList.size
                    it.contains(currencyNameList.first())
                })
        }
    }
}