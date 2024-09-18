package com.erayucar.kefabonelik.core.database.currency

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyRateEntity::class], version = 1, exportSchema = false)
abstract class CurrencyRateDatabase :RoomDatabase(){

    abstract fun currencyRateDao(): CurrencyRateDao
}