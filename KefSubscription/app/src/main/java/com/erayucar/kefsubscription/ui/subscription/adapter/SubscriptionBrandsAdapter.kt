package com.erayucar.kefabonelik.ui.subscription.adapter

import android.view.ViewGroup
import com.erayucar.kefabonelik.core.data.model.subscription.SubBrandModel
import com.erayucar.kefabonelik.core.ui.inflateAdapterItem
import com.erayucar.kefabonelik.databinding.SubscriptionBrandItemBinding
import com.erayucar.kefabonelik.ui.base.BaseAdapter
import com.erayucar.kefabonelik.ui.base.BaseViewHolder

class SubscriptionBrandsAdapter : BaseAdapter<SubBrandModel, BaseViewHolder<SubBrandModel>>() {
    private var onSubscriptionBrandItemClickListener: ((Int) -> Unit)? = null

    fun setOnSubscriptionItemClickListener(onSubscriptionItemClickListener: ((Int) -> Unit)?) {
        this.onSubscriptionBrandItemClickListener = onSubscriptionItemClickListener
    }

    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SubBrandModel> {

        return SubscriptionBrandsViewHolder(
            parent.inflateAdapterItem(SubscriptionBrandItemBinding::inflate),
            onSubscriptionBrandItemClickListener
        )


    }

    override fun findItemViewType(position: Int): Int {
        return when (getItemAtPosition(position)) {
            is SubBrandModel -> 1
            else -> -1
        }
    }
}