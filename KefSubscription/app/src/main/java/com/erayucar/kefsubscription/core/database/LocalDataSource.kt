package com.erayucar.kefabonelik.core.database

import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {



    suspend fun addCurrency(currencyEntity: CurrencyRateEntity)
    suspend fun updateCurrency(currencyEntity: CurrencyRateEntity)
    suspend fun getAllCurrency(): Flow<List<CurrencyRateEntity>>
    suspend fun getLatestDate(): Flow<String>
    suspend fun addSubscription(subscriptionEntity: SubscriptionEntity)
    suspend fun deleteSubscription(subscriptionEntity: SubscriptionEntity)
    suspend fun updateSubscription(remainingDate: String, id: Int)
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>
    fun getSubscription(id: Int): Flow<SubscriptionEntity>
    fun getTotalPrice(): Flow<Int>

}