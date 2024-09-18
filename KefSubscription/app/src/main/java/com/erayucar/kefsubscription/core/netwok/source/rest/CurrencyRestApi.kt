package com.erayucar.kefabonelik.core.netwok.source.rest

import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseEuroModel
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseUsdModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyRestApi {

    @GET("latest")
    suspend fun getCurrencyFromEuro(
        @Query("access_key") accessKey : String = EURO_BASE_API_KEY,
    ): Response<CurrencyBaseEuroModel>


    @GET("latest")
    suspend fun getCurrencyFromUsd(
        @Query("apikey") apiKey : String = USD_BASE_API_KEY,
    ): Response<CurrencyBaseUsdModel>

companion object{
    private const val EURO_BASE_API_KEY = "a7c57f93dbd6929a0712de2f96c2911f"
    private const val USD_BASE_API_KEY = "cur_live_IkQY1XzL3HBKH2UAp2W9ty2hQYSwlKQ6DuMM7Ky0"
}
}
