package com.erayucar.kefabonelik.ui.subscription.adapter

import com.erayucar.kefabonelik.core.common.Brands
import com.erayucar.kefabonelik.databinding.SubscriptionManageItemLayoutBinding
import com.erayucar.kefabonelik.ui.subscription.SubscriptionCategory
import com.erayucar.kefabonelik.ui.base.BaseViewHolder
import com.squareup.picasso.Picasso

class SubscriptionManageViewHolder(
    private val binding: SubscriptionManageItemLayoutBinding,
    private val onSubscriptionItemClickListener: ((Int) -> Unit)?
) : BaseViewHolder<SubscriptionCategory.Subscription>(binding.root) {

    init {
        binding.root.setOnClickListener {

            onSubscriptionItemClickListener?.invoke(adapterPosition)
        }
    }


    override fun bind(item: SubscriptionCategory.Subscription) {
        with(binding) {
            item.subscription.subscription?.let { item ->
                val brandImageUrl =
                    Brands.getBrands().find { it.id == item.brandId}?.imageUrl
                Picasso.get().load(brandImageUrl).into(subLogoImageView)
                subscriptionNameTextView.text = item.name
                subscriptionBillingDateTextView.text = item.billingDate
            }

        }
    }


}