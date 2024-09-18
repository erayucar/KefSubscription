package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubscriptionUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository) {

    suspend operator fun invoke(id : Int): Flow<SubscriptionEntity> {
        return subscriptionRepository.getSubscription(id)

    }
}