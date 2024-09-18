package com.erayucar.kefabonelik.ui.subscription.adapter

import com.erayucar.kefabonelik.databinding.SubsCategoryItemBinding
import com.erayucar.kefabonelik.ui.subscription.SubscriptionCategory
import com.erayucar.kefabonelik.ui.base.BaseViewHolder

class SubscriptionCategoryViewHolder(
    private val binding: SubsCategoryItemBinding
) : BaseViewHolder<SubscriptionCategory.Category>(binding.root) {



    override fun bind(item: SubscriptionCategory.Category) {
        with(binding) {
            subscriptionCategoryTextView.text = item.category
        }
    }
}


