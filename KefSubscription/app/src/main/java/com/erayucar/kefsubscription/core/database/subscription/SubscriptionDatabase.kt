package com.erayucar.kefabonelik.core.database.subscription

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SubscriptionEntity::class], version = 1, exportSchema = false)
abstract class SubscriptionDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): SubscriptionDao
}