package com.erayucar.kefabonelik.ui.home.adapter

import android.view.ViewGroup
import com.erayucar.kefabonelik.core.ui.inflateAdapterItem
import com.erayucar.kefabonelik.databinding.AdapterUpcomingSubscriptionItemBinding
import com.erayucar.kefabonelik.ui.home.SubscriptionUiState
import com.erayucar.kefabonelik.ui.base.BaseAdapter
import com.erayucar.kefabonelik.ui.base.BaseViewHolder

class SubscriptionListAdapter: BaseAdapter<SubscriptionUiState, BaseViewHolder<SubscriptionUiState>>() {
    private var onSubscriptionItemClickListener: ((Int) -> Unit)? = null

    fun setOnSubscriptionItemClickListener(onSubscriptionItemClickListener: ((Int) -> Unit)?) {
        this.onSubscriptionItemClickListener = onSubscriptionItemClickListener
    }

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SubscriptionUiState> {

        return SubscriptionViewHolder(
            parent.inflateAdapterItem(AdapterUpcomingSubscriptionItemBinding::inflate),
            onSubscriptionItemClickListener
        )


    }

    override fun findItemViewType(position: Int): Int {
        return when (getItemAtPosition(position)) {
            is SubscriptionUiState -> ItemType.REAL_CONTENT.ordinal
            else -> -1
        }
    }
}

enum class ItemType {
    REAL_CONTENT,
    AD
}