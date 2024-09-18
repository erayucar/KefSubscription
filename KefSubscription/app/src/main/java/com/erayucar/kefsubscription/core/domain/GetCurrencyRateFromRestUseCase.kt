package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.data.model.currency.Currency
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyRateFromRestUseCase@Inject constructor(
    private val repository: SubscriptionRepository
){
    suspend fun getCurrencyRateEuro(): Flow<ResponseState<Currency>> = repository.getCurrencyRateEuro()
    suspend fun getCurrencyRateUsd():Flow<ResponseState<Currency>> = repository.getCurrencyRateUsd()
}