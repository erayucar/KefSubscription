package com.erayucar.kefabonelik.core.database

import com.erayucar.kefabonelik.core.database.currency.CurrencyRateDao
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionDao
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val subscriptionDao: SubscriptionDao,
    private val currencyDao: CurrencyRateDao
) : LocalDataSource {
    override suspend fun addCurrency(currencyEntity: CurrencyRateEntity) {
        currencyDao.addCurrencyRate(currencyEntity)
    }

    override suspend fun updateCurrency(currencyEntity: CurrencyRateEntity) {
        currencyDao.updateCurrencyRate(currencyEntity)
    }

    override suspend fun getAllCurrency(): Flow<List<CurrencyRateEntity>> {
        return currencyDao.getAllCurrency()
    }

    override suspend fun getLatestDate(): Flow<String> {
        return currencyDao.getLatestDate()
    }

    override suspend fun addSubscription(subscriptionEntity: SubscriptionEntity) {
        subscriptionDao.addSubscription(subscriptionEntity)
    }

    override suspend fun deleteSubscription(subscriptionEntity: SubscriptionEntity) {
        subscriptionDao.deleteSubscription(subscriptionEntity)
    }

    override suspend fun updateSubscription(remainingDate: String, id: Int) {
        subscriptionDao.updateSubscription(remainingDate,id)
    }

    override fun getAllSubscriptions(): Flow<List<SubscriptionEntity>> {

        return subscriptionDao.getAllSubscriptions()
    }

    override fun getSubscription(id: Int): Flow<SubscriptionEntity> {
        return subscriptionDao.getSubscription(id)
    }

    override fun getTotalPrice(): Flow<Int> {
        return subscriptionDao.getTotalPrice()
    }
}