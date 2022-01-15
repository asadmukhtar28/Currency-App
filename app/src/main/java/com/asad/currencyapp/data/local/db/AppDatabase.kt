package com.asad.currencyapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asad.currencyapp.data.local.db.dao.CurrenciesDao
import com.asad.currencyapp.data.local.db.models.CurrencyNames
import com.asad.currencyapp.data.local.db.models.CurrencyRates
import com.asad.currencyapp.utils.DATABASE_NAME

@Database(
    entities = [CurrencyRates::class, CurrencyNames::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}