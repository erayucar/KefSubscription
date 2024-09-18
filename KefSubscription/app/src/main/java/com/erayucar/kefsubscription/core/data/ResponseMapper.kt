package com.erayucar.kefabonelik.core.data

import com.erayucar.kefabonelik.core.data.model.currency.Currency
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseEuroModel
import com.erayucar.kefabonelik.core.netwok.dto.CurrencyBaseUsdModel
import org.jsoup.nodes.Document
import retrofit2.Response
import java.text.DecimalFormat

typealias RestCurrencyEuroResponse = Response<CurrencyBaseEuroModel>
typealias RestCurrencyUsdResponse = Response<CurrencyBaseUsdModel>


fun RestCurrencyEuroResponse.toCurrency(): Currency {
    return body()!!.rates.mapTo {
        Currency(
            name = "EUR",
            rate = it.TRY,
            date = body()!!.date
        )
    }

}

fun RestCurrencyUsdResponse.toUsdCurrency(): Currency {
    return body()!!.data.mapTo {
        Currency(
            name = "USD",
            rate = it.TRY.value,
            date = body()!!.meta.last_updated_at
        )
    }

}


fun Document.toCurrenciesBaseTry(): List<Currency> {
    val decimalFormat = DecimalFormat("#.###")
    val usdRate =

        this.select(".srbstPysDvz .tBody ul:nth-of-type(1) li:nth-of-type(4)").text()

    val formattedUsdRate = usdRate.replace(',', '.')


    val euroRate =
        this.select(".srbstPysDvz ul:nth-of-type(2) li:nth-of-type(4)").text()


    val formattedEurRate = euroRate.replace(',', '.')



    return listOf(
        Currency(
            name = "USD",
            rate = formattedUsdRate.toDouble(),
            date = ""
        ),
        Currency(
            name = "EUR",
            rate = formattedEurRate.toDouble(),
            date = ""
        )
    )
}

fun Document.toCurrenciesUsdEur() : List<Currency> {

    val eurUSDRate =

        this.select("ul:nth-of-type(2) li.cell022").text()

    val formattedEurUsdRate = eurUSDRate.replace(',', '.')


    val usdEurRate =
        this.select("ul:nth-of-type(1) li.cell022.arrow").text()


    val formattedUsdEurRate = usdEurRate.replace(',', '.')



    return listOf(
        Currency(
            name = "EUR/USD",
            rate = formattedEurUsdRate.toDouble(),
            date = ""
        ),
        Currency(
            name = "USD/EUR",
            rate = formattedUsdEurRate.toDouble(),
            date = ""
        )
    )


}

