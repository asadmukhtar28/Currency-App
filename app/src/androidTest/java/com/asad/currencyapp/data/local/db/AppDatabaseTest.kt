package com.asad.currencyapp.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.asad.currencyapp.data.local.db.dao.CurrenciesDao
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.utils.MockTestUtils
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class AppDatabaseTest : TestCase() {

    private lateinit var currenciesDao: CurrenciesDao
    private lateinit var db: AppDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        currenciesDao = db.currenciesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testFetchCurrencyQuotes() = runBlocking {
        val rateList: List<CurrencyRates> = MockTestUtils.fetchCurrencyQuotes().run {
            quotes.toList().map { pair ->
                CurrencyRates(
                    pair.first.substring(3, pair.first.length),
                    pair.second
                )
            }
        }

        currenciesDao.insertCurrencyQuotes(rateList)
        val data = currenciesDao.getAllCurrencyQuotes().first()

        assertThat(data, CoreMatchers.notNullValue())
        assertThat(data.size, CoreMatchers.`is`(rateList.size))

        data.forEachIndexed { index, currencyRate ->
            assertThat(currencyRate.currencyName, equalTo(rateList[index].currencyName))
        }
    }

    @Test
    @Throws(Exception::class)
    fun testFetchCurrencyNames() = runBlocking {
        val nameList: List<CurrencyNames> = MockTestUtils.fetchCurrencyNames().run {
            currencies.toList().map { pair ->
                CurrencyNames(
                    pair.first.substring(3, pair.first.length),
                    pair.second
                )
            }
        }

        currenciesDao.insertCurrencyNames(nameList)
        val data = currenciesDao.getAllCurrencyNames().first()

        assertThat(data, CoreMatchers.notNullValue())
        assertThat(data.size, CoreMatchers.`is`(nameList.size))

        data.forEachIndexed { index, currencyRate ->
            assertThat(currencyRate.currencyName, equalTo(nameList[index].currencyName))
        }
    }
}