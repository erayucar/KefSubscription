package com.erayucar.kefsubscription.ui.subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.kefabonelik.core.common.Brands
import com.erayucar.kefabonelik.core.data.model.subscription.SubBrandModel
import com.erayucar.kefabonelik.core.domain.GetAllSubscriptionsUseCase
import com.erayucar.kefabonelik.ui.home.SubscriptionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageSubscriptionViewModel @Inject constructor(
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
) : ViewModel() {


    private val _subscriptionsBrandUiState = MutableLiveData<List<SubBrandModel>>()
    val subscriptionsBrandUiState: LiveData<List<SubBrandModel>> get() = _subscriptionsBrandUiState
    private val _subscriptionManageUiState =
        MutableLiveData<SubscriptionManageScreenUiState>()
    val subscriptionManageUiState: LiveData<SubscriptionManageScreenUiState> get() = _subscriptionManageUiState

    fun groupSubscriptionsByCategory() {
        viewModelScope.launch {
            getAllSubscriptionsUseCase().collect { subscriptions ->
                val groupedSubscriptions = subscriptions.groupBy { it.category }

                val categoryItems = mutableListOf<SubscriptionCategory>()

                groupedSubscriptions.forEach { (category, subscriptionsInCategory) ->
                    // Kategori öğesini ekle
                    categoryItems.add(SubscriptionCategory.Category(category))

                    // Abonelikleri kategori altında ekleyin
                    subscriptionsInCategory.forEach { subscription ->
                        val subscriptionUiState = SubscriptionUiState(subscription)
                        categoryItems.add(SubscriptionCategory.Subscription(subscriptionUiState))
                    }
                }

                // Güncellenmiş kategori listesini UI'ya yayınla
                _subscriptionManageUiState.postValue(SubscriptionManageScreenUiState(data = categoryItems))
            }
        }
    }



    fun getAllSubscriptionBrands() {
        viewModelScope.launch {

            val subBrand = Brands.getBrands()
            _subscriptionsBrandUiState.postValue(subBrand)
        }
    }


}

data class SubscriptionManageScreenUiState (


    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "",
    val data: List<SubscriptionCategory> = emptyList()

)

sealed class SubscriptionCategory {
    data class Category(val category: String) : SubscriptionCategory()

    data class Subscription(val subscription: SubscriptionUiState) : SubscriptionCategory()
}