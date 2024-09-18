package com.erayucar.kefabonelik.ui.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.erayucar.kefabonelik.R
import com.erayucar.kefabonelik.databinding.FragmentSettingsBinding
import com.erayucar.kefabonelik.ui.base.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    val viewModel by viewModels<SettingsViewModel>()



}