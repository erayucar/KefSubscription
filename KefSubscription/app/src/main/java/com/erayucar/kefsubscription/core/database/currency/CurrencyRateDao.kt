package com.erayucar.kefabonelik.core.database.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRateDao {

    @Insert
    suspend fun addCurrencyRate(currencyRateEntity: CurrencyRateEntity)

    @Update(entity = CurrencyRateEntity::class)
    suspend fun updateCurrencyRate(currencyRateEntity: CurrencyRateEntity)
    @Query("SELECT * FROM currency_rate_table ")
    fun getAllCurrency(): Flow<List<CurrencyRateEntity>>

    @Query("SELECT date FROM currency_rate_table ORDER BY date DESC LIMIT 1")
    fun getLatestDate(): Flow<String>
}