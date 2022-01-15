package com.asad.currencyapp.di.module

import android.content.Context
import com.asad.currencyapp.data.local.db.AppDatabase
import com.asad.currencyapp.data.local.db.dao.CurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideCurrenciesDao(appDatabase: AppDatabase): CurrenciesDao {
        return appDatabase.currenciesDao()
    }

}