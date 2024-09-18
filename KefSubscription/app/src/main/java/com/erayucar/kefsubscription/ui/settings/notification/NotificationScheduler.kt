package com.erayucar.kefabonelik.ui.settings.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class NotificationScheduler(private val context: Context) {

    @SuppressLint("ServiceCast")
    fun scheduleNotification(notificationTime: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManager::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Belirtilen saatte bir bildirim göndermek için AlarmManager'ı kullanma
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}