package com.erayucar.kefabonelik.ui.home.adapter

import android.annotation.SuppressLint
import android.view.View.INVISIBLE
import com.erayucar.kefabonelik.R
import com.erayucar.kefabonelik.core.common.Brands
import com.erayucar.kefabonelik.databinding.AdapterUpcomingSubscriptionItemBinding
import com.erayucar.kefabonelik.ui.home.SubscriptionUiState
import com.erayucar.kefabonelik.ui.base.BaseViewHolder
import com.squareup.picasso.Picasso

class SubscriptionViewHolder(
    private val binding: AdapterUpcomingSubscriptionItemBinding,
    private val onSubscriptionItemClickListener: ((Int) -> Unit)?
) : BaseViewHolder<SubscriptionUiState>(binding.root) {

    init {
        binding.root.setOnClickListener {
            onSubscriptionItemClickListener?.invoke(adapterPosition)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun bind(item: SubscriptionUiState) {
        with(binding) {
            item.subscription?.let { item ->
                Brands.getBrands().find { it.id == item.brandId }?.let {

                    Picasso.get().load(it.imageUrl).into(subLogoImageView)
                }
                subscriptionNameTextView.text = item.name
                subscriptionPriceTextView.text = item.price.toString()
                currencyTextView.text = item.currency
                subscriptionEndDateTextView.text = item.paymentDate
                if (item.remainingDate.toInt() == 0) {
                    subscriptionRemainingCountTextView.visibility = INVISIBLE
                    subscriptionRemainingDayTextView.text = TODAY
                } else if (item.remainingDate.toInt() == 1) {
                    subscriptionRemainingCountTextView.text = item.remainingDate
                    subscriptionRemainingDayTextView.text = DAY
                } else if (item.remainingDate.toInt() > 1){
                    subscriptionRemainingCountTextView.text = item.remainingDate
                    subscriptionRemainingDayTextView.text = DAYS
                }else if (item.remainingDate.toInt() < 0){
                    subscriptionItemLinearLayout.background = itemView.context.getDrawable(R.drawable.rectangle_red_border)
                    subscriptionRemainingCountTextView.visibility = INVISIBLE
                    subscriptionRemainingDayTextView.text = EXPIRED
                }


            }
        }

    }


    companion object {
        private const val TURKISH_LIRA = "₺"
        private const val TODAY = "Today"
        private const val DAY = "Day"
        private const val DAYS = "Days"
        private const val EXPIRED = "Expired"

    }

}
