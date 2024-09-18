package com.erayucar.kefabonelik.ui.settings.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.erayucar.kefabonelik.R

class NotificationHelper(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title: String, content: String) {
        // Bildirim oluşturma işlemleri burada yapılır
        // ...

        // Örnek bildirim gönderme kodu
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        const val CHANNEL_ID = "payment_notification_channel"

    }
}