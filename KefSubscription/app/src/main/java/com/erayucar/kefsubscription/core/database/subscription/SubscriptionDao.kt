package com.erayucar.kefabonelik.core.database.subscription

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubscription(subscriptionEntity: SubscriptionEntity)

    @Delete
    suspend fun deleteSubscription(subscriptionEntity: SubscriptionEntity)

    @Query("UPDATE subscription_table SET remaining_date = :remainingDate WHERE id = :id")
    suspend fun updateSubscription(remainingDate: String, id: Int)

    @Query("SELECT * FROM subscription_table ORDER BY id ASC")
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscription_table WHERE id = :id")
    fun getSubscription(id: Int): Flow<SubscriptionEntity>


    @Query("SELECT SUM(price) price FROM subscription_table ORDER BY id ASC")
    fun getTotalPrice(): Flow<Int>
}