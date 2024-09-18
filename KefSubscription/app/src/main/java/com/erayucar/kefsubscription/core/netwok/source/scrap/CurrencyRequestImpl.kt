package com.erayucar.kefabonelik.core.netwok.source.scrap

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

class CurrencyRequestImpl @Inject constructor() : CurrencyRequest {

    override fun getCurrencyBaseTryDoc(): Document {
        val url = "https://bigpara.hurriyet.com.tr/doviz/dolar/"
        return Jsoup.connect(url).get()
    }

    override fun getCurrencyUsdEurDoc(): Document {
        val url = "https://bigpara.hurriyet.com.tr/doviz/"
        return Jsoup.connect(url).get()
    }
}
