package com.erayucar.kefabonelik.core.netwok.dto

data class CurrencyBaseEuroModel(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)