package com.erayucar.kefabonelik.ui.subscription

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayucar.kefabonelik.databinding.FragmentSubscriptionBinding
import com.erayucar.kefabonelik.ui.subscription.adapter.SubscriptionManageAdapter
import com.erayucar.kefabonelik.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionFragment :
    BaseFragment<FragmentSubscriptionBinding>(FragmentSubscriptionBinding::inflate) {
    val viewModel by viewModels<ManageSubscriptionViewModel>()

    val groupedAdapter = SubscriptionManageAdapter().apply {
        setOnSubscriptionItemClickListener {position ->
            val id = (getItemAtPosition(position) as SubscriptionCategory.Subscription).subscription.subscription?.let {
                it.id
            }
            val action = SubscriptionFragmentDirections.actionSubscriptionFragmentToSubscriptionDetailFragment(id!!)
            navigate(action)
        }


    }

    override fun callInitialViewModelFunctions() {
        viewModel.groupSubscriptionsByCategory()
    }

    override fun observeUi() {


        viewModel.subscriptionManageUiState.observe(viewLifecycleOwner) {

            when {
                it.isError -> {
                    handleError(errorMessage = it.errorMessage!!)
                }

                it.isLoading -> {
                    handleLoading()
                }

                else -> {
                    handleSuccessResponse(it.data)
                }

            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccessResponse(subscriptions: List<SubscriptionCategory>) {
        groupedAdapter.updateItems(subscriptions)
        binding.recyclerView.adapter = groupedAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addSubLinearlayout.setOnClickListener {
            val action = SubscriptionFragmentDirections.actionSubscriptionFragmentToSubscriptionBrandsFragment()
            navigate(action)
        }
    }


    private fun handleLoading() {

    }

    private fun handleError(errorMessage: String) {

    }

}

