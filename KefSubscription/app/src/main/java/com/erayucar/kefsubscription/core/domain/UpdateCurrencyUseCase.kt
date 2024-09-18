package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import javax.inject.Inject

class UpdateCurrencyUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) {

    suspend operator fun invoke(
       currencyRateEntity: CurrencyRateEntity
    ) = subscriptionRepository.updateCurrency(currencyRateEntity)
}