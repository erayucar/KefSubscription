package com.erayucar.kefabonelik.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.kefabonelik.core.common.ResponseState
import com.erayucar.kefabonelik.core.database.currency.CurrencyRateEntity
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import com.erayucar.kefabonelik.core.domain.AddCurrencyUseCase
import com.erayucar.kefabonelik.core.domain.AddSubscriptionUseCase
import com.erayucar.kefabonelik.core.domain.GetAllCurrencyRateUseCase
import com.erayucar.kefabonelik.core.domain.GetAllSubscriptionsUseCase
import com.erayucar.kefabonelik.core.domain.GetCurrenciesFromScrapUseCase
import com.erayucar.kefabonelik.core.domain.GetCurrencyRateFromRestUseCase
import com.erayucar.kefabonelik.core.domain.GetLatestDateUseCase
import com.erayucar.kefabonelik.core.domain.GetTotalPriceUseCase
import com.erayucar.kefabonelik.core.domain.UpdateSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val getTotalPriceUseCase: GetTotalPriceUseCase,
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
    private val getCurrencyRateFromRestUseCase: GetCurrencyRateFromRestUseCase,
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val getAllCurrencyRateUseCase: GetAllCurrencyRateUseCase,
    private val getLatestDateUseCase: GetLatestDateUseCase,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    private val getCurrenciesFromScrapUseCase: GetCurrenciesFromScrapUseCase
) : ViewModel() {

    private val _subscriptionsUiState = MutableLiveData<SubscriptionHomeScreenUiState>()
    val subscriptionsUiState: LiveData<SubscriptionHomeScreenUiState> get() = _subscriptionsUiState

    private val _totalPriceUiState = MutableLiveData<String>()
    val totalPriceUiState: LiveData<String> get() = _totalPriceUiState

    private val _subscriptions = MutableLiveData<List<SubscriptionEntity>>()

    private val _currencyUiState = MutableLiveData<List<CurrencyRateEntity>>()

    private val _currencyLatestDate = MutableLiveData<String>()


    fun getCurrencyRatesFromRest() {
        viewModelScope.launch {
            getCurrencyRateFromRestUseCase.getCurrencyRateEuro().collect {
                when (it) {
                    is ResponseState.Error -> {

                    }

                    ResponseState.Loading -> {
                    }

                    is ResponseState.Success -> {
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data.name,
                                rate = it.data.rate,
                                date = it.data.date
                            )
                        )
                    }
                }


            }

            getCurrencyRateFromRestUseCase.getCurrencyRateUsd().collect {
                when (it) {
                    is ResponseState.Error -> {

                    }

                    ResponseState.Loading -> {
                    }

                    is ResponseState.Success -> {
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data.name,
                                rate = it.data.rate,
                                date = it.data.date
                            )
                        )
                    }
                }


            }

        }

    }

    fun getAllCurrencyRate() {
        viewModelScope.launch {
            getAllCurrencyRateUseCase().collect {
                _currencyUiState.value = it

            }
        }
    }


    fun getAllSubscriptions() {
        viewModelScope.launch {
            getAllSubscriptionsUseCase().collect { subscriptions ->
                _subscriptions.value = subscriptions
                _subscriptionsUiState.postValue(
                    SubscriptionHomeScreenUiState(
                        subscriptions = subscriptions.map { subscription ->
                            SubscriptionUiState(
                                subscription = subscription
                            )
                        }
                    )
                )
            }

        }
    }

    fun updateSubscription() {
        viewModelScope.launch {
            _subscriptions.value?.forEach { subscription ->
                val paymentDate = subscription.paymentDate
                val remainingDays = calculateRemainingDays(paymentDate)
                updateSubscriptionUseCase(remainingDays.toString(), subscription.id)

            }
        }
    }

    fun getTotalPrice(currency: String) {
        viewModelScope.launch {
            var totalprice: Double = 0.00

            _subscriptionsUiState.value?.subscriptions?.forEach {
                val itemCurrency = it.subscription?.currency
                var itemPrice = it.subscription?.price
                if (it.subscription?.subscriptionType == "Yearly") {
                    itemPrice = it.subscription?.price?.div(12)
                }
                itemPrice?.let { price ->
                    if (itemCurrency == currency) {
                        totalprice += price
                    } else if (itemCurrency == EURO && currency == TRY) {
                        totalprice += price * _currencyUiState.value?.find { it.name == EURO }?.rate!!
                    } else if (itemCurrency == USD && currency == TRY) {
                        totalprice += price * _currencyUiState.value?.find { it.name == USD }?.rate!!
                    } else if (itemCurrency == TRY && currency == EURO) {
                        totalprice += price / _currencyUiState.value?.find { it.name == EURO }?.rate!!
                    } else if (itemCurrency == TRY && currency == USD) {
                        totalprice += it.subscription.price / _currencyUiState.value?.find { it.name == USD }?.rate!!
                    } else if (itemCurrency == EURO && currency == USD) {
                        totalprice += price * _currencyUiState.value?.find { it.name == "$EURO/$USD" }?.rate!!
                        // totalprice += it.subscription.price * _currencyUiState.value?.find { it.name == EURO }?.rate!! / _currencyUiState.value?.find { it.name == "$EURO/$USD" }?.rate?.toInt()!!
                    } else if (itemCurrency == USD && currency == EURO) {
                        totalprice =
                            price * _currencyUiState.value?.find { it.name == "$USD/$EURO" }?.rate!!
                        //  totalprice += it.subscription.price * _currencyUiState.value?.find { it.name == USD }?.rate!! / _currencyUiState.value?.find { it.name == "$USD/$EURO" }?.rate?.toInt()!!
                    }
                }

            }
            val decimalFormat = DecimalFormat("#.##")
            _totalPriceUiState.value = decimalFormat.format(totalprice)


            /* getTotalPriceUseCase().collect { totalPrice ->

                 if (currency == "TRY") {
                     _totalPriceUiState.value = totalPrice.toString()
                 } else {
                     _currencyUiState.value?.find { it.name == currency }?.let {
                         val decimalFormat = DecimalFormat("#.##")
                         _totalPriceUiState.value = decimalFormat.format(totalPrice / it.rate)
                     }
                 }

             }*/

        }
    }

    fun getCurrenciesEurUsd() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrenciesFromScrapUseCase.getCurrencyRateEurUsd().collect {
                when (it) {
                    is ResponseState.Error -> {

                    }

                    ResponseState.Loading -> {
                    }

                    is ResponseState.Success -> {
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data[0].name,
                                rate = it.data[0].rate,
                                date = it.data[0].date
                            )
                        )
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data[1].name,
                                rate = it.data[1].rate,
                                date = it.data[1].date
                            )
                        )
                    }
                }


            }


        }


    }

    fun getCurrenciesBaseTry() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrenciesFromScrapUseCase.getCurencisBaseTry().collect {
                when (it) {
                    is ResponseState.Error -> {

                    }

                    ResponseState.Loading -> {
                    }

                    is ResponseState.Success -> {
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data[0].name,
                                rate = it.data[0].rate,
                                date = it.data[0].date
                            )
                        )
                        addCurrencyUseCase(
                            CurrencyRateEntity(
                                name = it.data[1].name,
                                rate = it.data[1].rate,
                                date = it.data[1].date
                            )
                        )
                    }
                }


            }

        }


    }

    fun addSubscription(
        brandId: Int,
        category: String,
        name: String,
        startDate: String,
        price: String,
        subscriptionType: String,
        currency: String
    ) {
        viewModelScope.launch {
            val endDate = calculateEndDate(startDate, subscriptionType)
            val remainingDays = calculateRemainingDays(endDate)

            val subscription = SubscriptionEntity(
                name = name,
                billingDate = startDate,
                paymentDate = endDate,
                remainingDate = remainingDays.toString(),
                price = price.toDoubleOrNull() ?: 0.00,
                brandId = brandId,
                category = category,
                subscriptionType = subscriptionType,
                currency = currency
            )
            addSubscriptionUseCase(subscription)
        }
    }

    fun getLatestDate() {
        viewModelScope.launch {
            getLatestDateUseCase().collect {
                _currencyLatestDate.value = it
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkRestApiTime() {

        if (_currencyLatestDate.value != null) {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val latestTime = LocalDate.parse(_currencyLatestDate.value, formatter)

            val period = Period.between(currentDate, latestTime)

            if (period.days >= 1) {
                getCurrencyRatesFromRest()
            }

        } else {
            getCurrencyRatesFromRest()
        }

    }

    companion object {
        const val EURO = "EUR"
        const val USD = "USD"
        const val TRY = "TRY"
    }



}


data class SubscriptionHomeScreenUiState(


    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "",
    val subscriptions: List<SubscriptionUiState> = emptyList(),

    )

data class SubscriptionUiState(
    val subscription: SubscriptionEntity? = null,
)



