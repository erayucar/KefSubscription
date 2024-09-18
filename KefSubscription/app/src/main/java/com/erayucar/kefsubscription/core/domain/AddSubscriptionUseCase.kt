package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import javax.inject.Inject

class AddSubscriptionUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: SubscriptionEntity) {
        subscriptionRepository.addSubscription(subscription)
    }
}