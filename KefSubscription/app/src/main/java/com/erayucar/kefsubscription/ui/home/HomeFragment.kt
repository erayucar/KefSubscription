package com.erayucar.kefsubscription.ui.home

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentSubscriptionListBinding>(FragmentSubscriptionListBinding::inflate) {

    private val viewModel by viewModels<SubscriptionsViewModel>()
    private val adapter = SubscriptionListAdapter().apply {
        setOnSubscriptionItemClickListener {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun callInitialViewModelFunctions() {
        viewModel.getAllSubscriptions()
        viewModel.getCurrenciesBaseTry()
        viewModel.getCurrenciesEurUsd()
        viewModel.getAllCurrencyRate()
       // viewModel.getLatestDate()
        //viewModel.checkRestApiTime()
        viewModel.updateSubscription()




    }

    fun checkNotificationPermission(){

    }

    override fun observeUi() {



        viewModel.subscriptionsUiState.observe(viewLifecycleOwner) {
            when {

                it.isError -> {
                    handleError(errorMessage = it.errorMessage!!)
                }

                it.isLoading -> {
                    handleLoading()
                }

               else -> {
                    handleSuccessResponse(it.subscriptions)


                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSuccessResponse(subscriptions: List<SubscriptionUiState>) {

        viewModel.getTotalPrice(binding.currencyTextView.text.toString())
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateSubscription()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.currencyTextView.setOnClickListener {
            val popupMenu = PopupMenu(requireActivity(), binding.currencyTextView)
            popupMenu.menuInflater.inflate(R.menu.currency_type_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                binding.currencyTextView.text = menuItem.title
                viewModel.getTotalPrice(binding.currencyTextView.text.toString())
                true
            }
            popupMenu.show()
        }
        binding.addSubLinearlayout.setOnClickListener {
            val action = HomeFragmentDirections.actionSubscriptionListFragmentToSubscriptionBrandsFragment()
            navigate(action)
        }
        adapter.updateItems(subscriptions)
        binding.subscriptionListRecyclerView.adapter = adapter
        binding.subscriptionListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.totalPriceUiState.observe(viewLifecycleOwner) { it
            binding.totalMonthlyCostTextView.text = it}

      /*  binding.floatingActionButton.isClickable = true
        binding.floatingActionButton.setOnClickListener {
            val action = SubscriptionListFragmentDirections.actionSubscriptionListFragmentToAddFragment()
            navigate(action)
        }*/

    }
}