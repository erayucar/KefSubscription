package com.erayucar.kefabonelik.ui.subscription.adapter

import android.view.ViewGroup
import com.erayucar.kefabonelik.core.ui.inflateAdapterItem
import com.erayucar.kefabonelik.databinding.SubsCategoryItemBinding
import com.erayucar.kefabonelik.databinding.SubscriptionManageItemLayoutBinding
import com.erayucar.kefabonelik.ui.subscription.SubscriptionCategory
import com.erayucar.kefabonelik.ui.base.BaseAdapter
import com.erayucar.kefabonelik.ui.base.BaseViewHolder

class SubscriptionManageAdapter :
    BaseAdapter<SubscriptionCategory, BaseViewHolder<SubscriptionCategory>>() {
    private var onSubscriptionItemClickListener: ((Int) -> Unit)? = null

    fun setOnSubscriptionItemClickListener(onSubscriptionItemClickListener: ((Int) -> Unit)?) {
        this.onSubscriptionItemClickListener = onSubscriptionItemClickListener
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SubscriptionCategory> {
        return when (viewType) {

        VIEW_TYPE_CATEGORY-> {
                SubscriptionCategoryViewHolder(
                    parent.inflateAdapterItem(SubsCategoryItemBinding::inflate)
                )
            }

            VIEW_TYPE_SUBSCRIPTION -> {
                SubscriptionManageViewHolder(
                    parent.inflateAdapterItem(SubscriptionManageItemLayoutBinding::inflate),
                    onSubscriptionItemClickListener
                )
            }

            else -> throw Exception("Can not be constructed view holder with type:$viewType")
        }
    }

    override fun findItemViewType(position: Int): Int {
        return when (getItemAtPosition(position)) {
            is SubscriptionCategory.Category -> VIEW_TYPE_CATEGORY
            is SubscriptionCategory.Subscription -> VIEW_TYPE_SUBSCRIPTION
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }
    companion object {
        private const val VIEW_TYPE_CATEGORY = 1
        private const val VIEW_TYPE_SUBSCRIPTION = 2
    }
}