package com.erayucar.kefabonelik.ui.settings.notification

import android.content.Context
import java.util.Calendar

class NotificationSettings(private val context: Context) {



    fun getNotificationTime(time : String): Long {
        val timeString = time
        val timeArray = timeString.split(":")

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
            set(Calendar.MINUTE, timeArray[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return calendar.timeInMillis
    }
}