package com.asad.currencyapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.asad.currencyapp.utils.WORK_MANAGER_TAG
import com.asad.currencyapp.workers.FetchCurrencyQuotesWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CurrencyApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val appScope = CoroutineScope(Dispatchers.IO)

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .build()

    override fun onCreate() {
        super.onCreate()
        initWorkManager()
    }

    private fun initWorkManager() {
        appScope.launch {
            val worker = PeriodicWorkRequestBuilder<FetchCurrencyQuotesWorker>(
                30,
                TimeUnit.HOURS
            )
                .addTag(WORK_MANAGER_TAG)
                .setInitialDelay(0, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniquePeriodicWork(
                    WORK_MANAGER_TAG,
                    ExistingPeriodicWorkPolicy.KEEP,
                    worker
                )
        }
    }
}