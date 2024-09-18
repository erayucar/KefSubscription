package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSubscriptionsUseCase @Inject constructor(private val subscriptionRepository: SubscriptionRepository){
    suspend operator fun invoke(): Flow<List<SubscriptionEntity>> {
        return subscriptionRepository.getAllSubscriptions()

    }
}