package com.erayucar.kefsubscription.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.kefabonelik.core.database.subscription.SubscriptionEntity
import com.erayucar.kefabonelik.core.domain.DeleteSubscriptionUseCase
import com.erayucar.kefabonelik.core.domain.GetSubscriptionUseCase
import com.erayucar.kefabonelik.ui.home.SubscriptionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionDetailViewModel @Inject constructor(
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    private val getSubscriptionUseCase: GetSubscriptionUseCase
) : ViewModel() {
    private val _subscriptionsDetailScreenUiState =
        MutableLiveData<SubscriptionDetailScreenUiState>()
    val subscriptionsDetailScreenUiState: LiveData<SubscriptionDetailScreenUiState> get() = _subscriptionsDetailScreenUiState


    fun getSubscription(id: Int) {
        viewModelScope.launch {
            getSubscriptionUseCase(id = id).collect {
                _subscriptionsDetailScreenUiState.postValue(
                    SubscriptionDetailScreenUiState.Success(
                        SubscriptionUiState(
                            subscription = it
                        )
                    )
                )

            }
        }

    }

    /*fun updateSubscription(name: String, category: String, startDate: String, price: String) {
        viewModelScope.launch {
            val endDate = calculateEndDate(startDate)
            val remainingDate = calculateRemainingDays(endDate)

            val subscription = SubscriptionEntity(
                name = name,
                startDate = startDate,
                endDate = endDate,
                remainingDate = remainingDate.toString(),
                price = price.toInt(),
                category = category
            )

            updateSubscriptionUseCase(subscription)
        }
    }*/


    fun deleteSubscription(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            deleteSubscriptionUseCase(subscription)
        }
    }


}

sealed class SubscriptionDetailScreenUiState {


    object Loading : SubscriptionDetailScreenUiState()
    class Success(val data: SubscriptionUiState) : SubscriptionDetailScreenUiState()
    data class Error(val message: String) : SubscriptionDetailScreenUiState()

}

