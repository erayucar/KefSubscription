package com.erayucar.kefabonelik.ui.subscription.adapter

import com.erayucar.kefabonelik.core.data.model.subscription.SubBrandModel
import com.erayucar.kefabonelik.databinding.SubscriptionBrandItemBinding
import com.erayucar.kefabonelik.ui.base.BaseViewHolder
import com.squareup.picasso.Picasso

class SubscriptionBrandsViewHolder(
    private val binding: SubscriptionBrandItemBinding,
    private val onSubscriptionItemClickListener: ((Int) -> Unit)?
): BaseViewHolder<SubBrandModel>(binding.root) {

    init {
        binding.root.setOnClickListener {

            onSubscriptionItemClickListener?.invoke(adapterPosition)
        }
    }
    override fun bind(item: SubBrandModel) {
        with(binding){
            Picasso.get().load(item.imageUrl).into(subscriptionBrandImageView)
            subscriptionBrandTextView.text = item.name


        }
    }

}
