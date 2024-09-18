package com.erayucar.kefsubscription.ui.detail

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erayucar.kefabonelik.core.common.Brands
import com.erayucar.kefabonelik.databinding.FragmentSubscriptionDetailBinding
import com.erayucar.kefabonelik.ui.home.SubscriptionUiState
import com.erayucar.kefabonelik.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionDetailFragment :
    BaseFragment<FragmentSubscriptionDetailBinding>(FragmentSubscriptionDetailBinding::inflate) {

    private val viewModel by viewModels<SubscriptionDetailViewModel>()

    private val args: SubscriptionDetailFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun callInitialViewModelFunctions() {
        viewModel.getSubscription(args.subscriptionId)
    }

    override fun observeUi() {
        viewModel.subscriptionsDetailScreenUiState.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                is SubscriptionDetailScreenUiState.Error -> {

                }

                is SubscriptionDetailScreenUiState.Loading -> {

                }

                is SubscriptionDetailScreenUiState.Success -> {
                    handleSuccessResponse(it.data)
                }

                else -> {}
            }
        }
    }

    private fun handleSuccessResponse(subscriptionUiState: SubscriptionUiState) {

        with(binding) {
            val brand =
                Brands.getBrands().find { it.id == subscriptionUiState.subscription?.brandId }

            Picasso.get().load(brand?.imageUrl).into(subLogoImageView)


            subBackButton.setOnClickListener {
                navController.popBackStack()
            }
            subscriptionUiState.subscription?.let {
                subNameTextView.text = subscriptionUiState.subscription.name

                subPeriodTypeTextView.text = it.subscriptionType

                subCurrencyTextView.text =it.currency
                    subPriceTextView.text =
                    subscriptionUiState.subscription.price.toString()
                subBillingDateTextView.text = subscriptionUiState.subscription.billingDate
                subNextPaymentDateTextView.text = subscriptionUiState.subscription.paymentDate
                subCategoryTextView.text = it.category

                cancelSubLayout.setOnClickListener {
                    viewModel.deleteSubscription(subscriptionUiState.subscription)
                    Toast.makeText(requireContext(), "Subscription deleted successfully .", Toast.LENGTH_SHORT).show()
                    val action =
                        SubscriptionDetailFragmentDirections.actionSubscriptionDetailFragmentToSubscriptionFragment()
                    navigate(action)
                }

            }

        }

    }


}