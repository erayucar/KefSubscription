package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesFromScrapUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
){

    suspend  fun getCurencisBaseTry() : Flow<ResponseState<List<Currency>>> {
        return subscriptionRepository.getCurrencyBaseTryDoc()
    }

    suspend fun getCurrencyRateEurUsd(): Flow<ResponseState<List<Currency>>> {
        return subscriptionRepository.getCurrencyUsdEurDoc()
    }
}