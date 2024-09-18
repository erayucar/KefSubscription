package com.erayucar.kefabonelik.core.netwok.source.scrap

import org.jsoup.nodes.Document

interface CurrencyRequest {

    fun getCurrencyBaseTryDoc(): Document
    fun getCurrencyUsdEurDoc(): Document

}