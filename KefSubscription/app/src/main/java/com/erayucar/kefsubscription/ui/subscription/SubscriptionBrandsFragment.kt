package com.erayucar.kefabonelik.ui.subscription

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erayucar.kefabonelik.databinding.FragmentSubscriptionBrandsBinding
import com.erayucar.kefabonelik.ui.subscription.adapter.SubscriptionBrandsAdapter
import com.erayucar.kefabonelik.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionBrandsFragment : BaseFragment<FragmentSubscriptionBrandsBinding>(FragmentSubscriptionBrandsBinding::inflate) {
    val viewModel by viewModels<ManageSubscriptionViewModel>()
    private val navController by lazy { findNavController() }
    private val adapter  = SubscriptionBrandsAdapter().apply {
        setOnSubscriptionItemClickListener { it ->
            val item =  getItemAtPosition(it)
            item?.let {item ->
                val action = SubscriptionBrandsFragmentDirections.actionSubscriptionBrandsFragmentToAddFragment(item.id,item.category,
                    BRAND)
                navigate(action)
            }
        }
    }

    override fun callInitialViewModelFunctions() {
        viewModel.getAllSubscriptionBrands()
    }
    override fun observeUi() {
        viewModel.subscriptionsBrandUiState.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
            binding.subscriptionBrandRecyclerView.adapter = adapter
            binding.subscriptionBrandRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.subBackButton.setOnClickListener {
            navController.popBackStack()
        }
        binding.otherLinearLayout.setOnClickListener {
            val action = SubscriptionBrandsFragmentDirections.actionSubscriptionBrandsFragmentToAddFragment(0,"",
                OTHER)

            navigate(action)
        }
    }

    companion object{
        const val BRAND = "brand"
        const val OTHER = "other"
    }

}