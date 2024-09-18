package com.erayucar.kefabonelik.ui.settings.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Bildirim gönderme kodu buraya eklenecek
        if (context != null) {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showNotification("Bildirim Başlığı", "Bildirim İçeriği")
        }
    }
}