package com.halilozcan.andromeda.core.database.di

import android.content.Context
import androidx.room.Room
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateDatabase
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSubscriptionDatabase(
        @ApplicationContext context: Context
    ): SubscriptionDatabase = Room.databaseBuilder(
        context = context,
        klass = SubscriptionDatabase::class.java,
        name = "subscription_database"
    ).build()

    @Provides
    @Singleton
    fun provideCurrencyDatabase(
        @ApplicationContext context: Context
    ): CurrencyRateDatabase = Room.databaseBuilder(
        context = context,
        klass = CurrencyRateDatabase::class.java,
        name = "curreny_database"
    ).build()


    @Provides
    fun provideSubscriptionDao(database: SubscriptionDatabase) = database.favoriteCharacterDao()

    @Provides
    fun provideCurrencyDao(database: CurrencyRateDatabase) = database.currencyRateDao()
}