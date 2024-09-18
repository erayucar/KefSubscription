package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import javax.inject.Inject

class UpdateSubscriptionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {

    suspend operator fun invoke(remainingDate: String, id: Int) {
        subscriptionRepository.updateSubscription(remainingDate, id)
    }
}