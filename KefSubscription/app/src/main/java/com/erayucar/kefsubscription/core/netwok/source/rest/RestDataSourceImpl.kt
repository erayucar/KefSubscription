package com.erayucar.kefabonelik.core.netwok.source.rest

import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseEuroModel
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseUsdModel
import com.erayucar.kefabonelik.core.netwok.source.scrap.CurrencyRequest
import org.jsoup.nodes.Document
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class RestDataSourceImpl @Inject constructor(
    @Named("eurRetrofit") private val eurRetrofit: Retrofit,
    @Named("usdRetrofit") private val usdRetrofit: Retrofit
) : RestDataSource {

    private val eurApiService: CurrencyRestApi = eurRetrofit.create(CurrencyRestApi::class.java)
    private val usdApiService: CurrencyRestApi = usdRetrofit.create(CurrencyRestApi::class.java)

    override suspend fun getCurrencyFromEuro(): Response<CurrencyBaseEuroModel> {
        return eurApiService.getCurrencyFromEuro()
    }

    override suspend fun getCurrenctFromUsd(): Response<CurrencyBaseUsdModel> {
        return usdApiService.getCurrencyFromUsd()
    }
}