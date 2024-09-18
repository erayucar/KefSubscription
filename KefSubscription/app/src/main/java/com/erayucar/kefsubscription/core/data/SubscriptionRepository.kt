package com.erayucar.kefabonelik.core.data

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.data.model.currency.Currency
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import org.jsoup.nodes.Document

interface SubscriptionRepository {

    suspend fun getCurrencyRateEuro(): Flow<ResponseState<Currency>>
    suspend fun getCurrencyRateUsd(): Flow<ResponseState<Currency>>
    suspend fun addCurrency(currency: CurrencyRateEntity)
    suspend fun getAllCurrency(): Flow<List<CurrencyRateEntity>>
    suspend fun getLatestDate(): Flow<String>
    suspend fun updateCurrency(currency: CurrencyRateEntity)
    suspend fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>
    suspend fun updateSubscription(remainingDate: String, id: Int)
    suspend fun getSubscription(id: Int): Flow<SubscriptionEntity>
    suspend fun addSubscription(subscription: SubscriptionEntity)
    suspend fun deleteSubscription(subscription: SubscriptionEntity)
    suspend fun getTotalPrice(): Flow<Int>
    suspend fun getCurrencyBaseTryDoc () : Flow<ResponseState<List<Currency>>>
    suspend fun getCurrencyUsdEurDoc () : Flow<ResponseState<List<Currency>>>

}