package com.example.tbank.presentation.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.tbank.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val appCtx: Context
) {
    private val notificationManager = appCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels(){
        notificationManager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                appCtx.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    fun showNotification(title: String, message: String, intent: PendingIntent?){
        val notificationBuilder = NotificationCompat.Builder(appCtx, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentText(message)
            .setAutoCancel(true)

        intent?.let {
            notificationBuilder.setContentIntent(it)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val CHANNEL_ID = "important_notifications"
        const val NOTIFICATION_ID = 0
    }
}