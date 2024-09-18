package com.erayucar.kefsubscription


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.erayucar.kefabonelik.databinding.ActivityMainBinding
import com.erayucar.kefabonelik.ui.subscription.SubscriptionFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var lastSelectedItemId: Int = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var hasNotificationPermission = MutableLiveData(ContextCompat.checkSelfPermission(this,
            (Manifest.permission.POST_NOTIFICATIONS)) == PackageManager.PERMISSION_GRANTED)

        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            hasNotificationPermission.value = isGranted
        }

        if (hasNotificationPermission.value == false) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }






        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView

        // BottomNavigationView'daki öğeleri dinleyerek geri dönüş işlemini kontrol et
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == lastSelectedItemId) {
                // İki kez tıklandı, geri dönüş işlemini yap
                navController.navigate(item.itemId)
                return@setOnNavigationItemSelectedListener false
            }
            lastSelectedItemId = item.itemId
            NavigationUI.onNavDestinationSelected(item, navController)
        }





    }


}
