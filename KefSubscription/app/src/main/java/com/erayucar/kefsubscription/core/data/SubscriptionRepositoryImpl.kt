package com.erayucar.kefabonelik.core.data

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.data.model.currency.Currency
import com.erayucar.kefabonelik.core.database.LocalDataSource
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import com.erayucar.kefabonelik.core.netwok.source.rest.RestDataSource
import com.erayucar.kefabonelik.core.netwok.source.scrap.CurrencyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val restDataSource: RestDataSource,
    private val localDataSource: LocalDataSource,
    private val currencyRequest: CurrencyRequest
): SubscriptionRepository {
    override suspend fun getCurrencyRateEuro(): Flow<ResponseState<Currency>> {
        return flow {
            emit(ResponseState.Loading)
            val response = restDataSource.getCurrencyFromEuro()
            emit(ResponseState.Success(response.mapTo { it.toCurrency()}))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override suspend fun getCurrencyRateUsd(): Flow<ResponseState<Currency>> {
        return flow {
            emit(ResponseState.Loading)
            val response = restDataSource.getCurrenctFromUsd()
            emit(ResponseState.Success(response.mapTo { it.toUsdCurrency()}))
        }.catch {
            emit(ResponseState.Error(it.message.orEmpty()))
        }
    }

    override suspend fun addCurrency(currency: CurrencyRateEntity) {
        localDataSource.addCurrency(currency)
    }

    override suspend fun getAllCurrency(): Flow<List<CurrencyRateEntity>> {
        return localDataSource.getAllCurrency()
    }

    override suspend fun getLatestDate(): Flow<String> {
        return localDataSource.getLatestDate()
    }

    override suspend fun updateCurrency(currency: CurrencyRateEntity) {
        localDataSource.updateCurrency(currency)
    }


    override suspend fun getAllSubscriptions(): Flow<List<SubscriptionEntity>> {
        return localDataSource.getAllSubscriptions()
    }

    override suspend fun updateSubscription(remainingDate: String, id: Int) {
        localDataSource.updateSubscription(remainingDate,id)
    }

    override suspend fun getSubscription(id: Int): Flow<SubscriptionEntity> {
        return localDataSource.getSubscription(id)
    }

    override suspend fun addSubscription(subscription: SubscriptionEntity) {
        localDataSource.addSubscription(subscription)
    }

    override suspend fun deleteSubscription(subscription: SubscriptionEntity) {
        localDataSource.deleteSubscription(subscription)
    }

    override suspend fun getTotalPrice(): Flow<Int> {
        return localDataSource.getTotalPrice()
    }

    override suspend fun getCurrencyBaseTryDoc(): Flow<ResponseState<List<Currency>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = currencyRequest.getCurrencyBaseTryDoc()



            emit(ResponseState.Success(response.toCurrenciesBaseTry()))
        }.catch {
            print(it.message)
            emit(ResponseState.Error(it.message.orEmpty()))

        }
    }

    override suspend fun getCurrencyUsdEurDoc(): Flow<ResponseState<List<Currency>>> {
        return flow {
            emit(ResponseState.Loading)
            val response = currencyRequest.getCurrencyUsdEurDoc()
            emit(ResponseState.Success(response.toCurrenciesUsdEur()))
        }.catch {
            print(it.message)
            emit(ResponseState.Error(it.message.orEmpty()))

        }
    }

}