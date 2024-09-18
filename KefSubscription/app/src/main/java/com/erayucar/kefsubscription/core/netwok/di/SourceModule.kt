package com.erayucar.kefabonelik.core.netwok.di

import com.erayucar.kefabonelik.core.netwok.source.rest.RestDataSource
import com.erayucar.kefabonelik.core.netwok.source.rest.RestDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun bindRestDataSource(restDataSourceImpl: RestDataSourceImpl): RestDataSource
}