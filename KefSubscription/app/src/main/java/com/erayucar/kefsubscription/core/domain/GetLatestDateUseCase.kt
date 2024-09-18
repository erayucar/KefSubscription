package com.erayucar.kefabonelik.core.domain

import com.erayucar.kefabonelik.core.data.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestDateUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) {

    suspend operator fun invoke(): Flow<String> {
        return subscriptionRepository.getLatestDate()
    }
}