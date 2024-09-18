package com.erayucar.kefabonelik.ui.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.erayucar.kefabonelik.R
import com.erayucar.kefabonelik.core.common.Brands
import com.erayucar.kefabonelik.databinding.FragmentAddBinding
import com.erayucar.kefabonelik.ui.base.BaseFragment
import com.erayucar.kefabonelik.ui.home.SubscriptionsViewModel
import com.erayucar.kefabonelik.ui.home.isDateValid
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {
    private var mInterstitialAd: InterstitialAd? = null
    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-6818484763948305/4996162455",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })


        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private val viewModel by viewModels<SubscriptionsViewModel>()
    private lateinit var calendar: Calendar
    private val args: AddFragmentArgs by navArgs()


    @SuppressLint("SetTextI18n")
    override fun observeUi() {
        val source = args.sourceFragment

        when (source) {
            BRAND -> {
                val item = Brands.getBrands().find { it.id == args.brandId }
                item?.let {
                    Picasso.get().load(item?.imageUrl).into(binding.subLogoImageView)
                    binding.subNameTextView.isEnabled = false
                    binding.subCategoryTextView.setText(item.category)
                    binding.subNameTextView.setText(item.name)
                }
            }

            OTHER -> {
                binding.subCategoryTextView.hint = "Select the Category"
                binding.subCategoryTextView.setOnClickListener {
                    val popupMenu = PopupMenu(requireActivity(), binding.subCategoryTextView)
                    for (item in Brands.getBrandsCategory()) {
                        popupMenu.menu.add(item)
                    }
                    popupMenu.menuInflater.inflate(R.menu.category_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        binding.subCategoryTextView.setText(menuItem.title)
                        true
                    }
                    popupMenu.show()
                }
            }
        }



        binding.subBackButton.setOnClickListener {

            navigation.popBackStack()
        }
        binding.subCurrencyLayout.setOnClickListener {
            val popupMenu = PopupMenu(requireActivity(), binding.subCurrencyTextView)
            popupMenu.menuInflater.inflate(R.menu.currency_type_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                binding.subCurrencyTextView.text = menuItem.title

                true
            }
            popupMenu.show()
        }
        binding.subPeriodTypeTextView.text = subscriptionType[0]
        binding.subPeriodTypeLayout.setOnClickListener {
            val popupMenu = PopupMenu(requireActivity(), binding.subPeriodTypeLayout)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.subscription_type_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                binding.subPeriodTypeTextView.text = menuItem.title
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }


        calendar = Calendar.getInstance()

        binding.subBillingDateTextView.text = SimpleDateFormat(
            "dd/MM/yyyy", Locale.getDefault()
        ).format(calendar.time)





        binding.subBillingDateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        binding.addSubLineralayout.setOnClickListener {
            val name = binding.subNameTextView.text.toString()
            val price = binding.subPriceTextView.text.toString()
            val date = binding.subBillingDateTextView.text.toString()
            val subscriptionType = binding.subPeriodTypeTextView.text.toString()
            val currency = binding.subCurrencyTextView.text.toString()
            val category = binding.subCategoryTextView.text.toString()
            if (name.isEmpty() || price.isEmpty() || date.isEmpty()||category.isEmpty()) {
                Toast.makeText(
                    requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Mevcut tarihi al
            val currentDate = Calendar.getInstance().time

            // Seçilen tarihi tarih nesnesine çevir
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)

            // Seçilen tarih mevcut tarihten ileri bir tarihse hata mesajını göster
            if (selectedDate != null && selectedDate.after(currentDate)) {
                Toast.makeText(
                    requireContext(),
                    "Selected date must be on or before the current date.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (isDateValid(date, subscriptionType)) {

                viewModel.addSubscription(
                    brandId = args.brandId,
                    name = name,
                    price = price,
                    startDate = date,
                    category = category,
                    subscriptionType = subscriptionType,
                    currency = currency
                )

                // Diğer işlemleri gerçekleştirin
                val action = AddFragmentDirections.actionAddFragmentToSubscriptionFragment()
                Toast.makeText(requireContext(), "Saved successfully.", Toast.LENGTH_SHORT).show()
                navigate(action)
            } else {
                Toast.makeText(
                    requireContext(), "Selected date must be within a month.", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            }
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Toast.makeText(
                    requireContext(),
                    "The interstitial ad wasn't ready yet.",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }

    private fun showDatePickerDialog() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateEditText()
            }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateEditText() {
        val dateFormat = "dd/MM/yyyy" // İsteğe bağlı olarak tarih formatını değiştirin
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        binding.subBillingDateTextView.text = simpleDateFormat.format(calendar.time)
    }


    companion object {
        val subscriptionType = arrayOf("Monthly", "Yearly")
        val BRAND = "brand"
        val OTHER = "other"
    }

}