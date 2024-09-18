package com.erayucar.kefabonelik.core.netwok.di

import com.erayucar.kefabonelik.core.netwok.source.scrap.CurrencyRequest
import com.erayucar.kefabonelik.core.netwok.source.rest.CurrencyRestApi
import com.erayucar.kefabonelik.core.netwok.source.scrap.CurrencyRequestImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val REST_EUR_API_BASE_URL = "http://data.fixer.io/api/"
    private const val REST_USD_API_BASE_URL = "https://api.currencyapi.com/v3/"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("eurRetrofit")
    fun provideEurRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REST_EUR_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("usdRetrofit")
    fun provideUsdRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REST_USD_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyRequest(impl: CurrencyRequestImpl): CurrencyRequest {
        return impl
    }

    @Provides
    @Singleton
    fun provideCurrencyRestApi(@Named("eurRetrofit") retrofit: Retrofit): CurrencyRestApi {
        return retrofit.create(CurrencyRestApi::class.java)
    }
}