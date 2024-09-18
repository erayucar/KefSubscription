package com.erayucar.kefabonelik

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.erayucar.kefabonelik.core.InternetUtil
import com.erayucar.kefabonelik.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternet()
        }



    private fun checkInternet() {
        if (InternetUtil.isInternetAvailable(applicationContext)) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                // You can add additional actions here if needed.
            }
            .setCancelable(false) // Prevent the dialog from being dismissed with the back button
            .show()
    }
}
