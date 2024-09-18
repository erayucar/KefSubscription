package com.erayucar.kefabonelik.core.netwok.source.rest

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseEuroModel
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseUsdModel
import org.jsoup.nodes.Document
import retrofit2.Response

interface RestDataSource {


    suspend fun getCurrencyFromEuro(): Response<CurrencyBaseEuroModel>


    suspend fun getCurrenctFromUsd(): Response<CurrencyBaseUsdModel>



}
