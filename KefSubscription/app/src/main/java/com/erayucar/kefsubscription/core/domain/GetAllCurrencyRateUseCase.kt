package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCurrencyRateUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
){

    suspend operator fun invoke() : Flow<List<CurrencyRateEntity>> = subscriptionRepository.getAllCurrency()
}