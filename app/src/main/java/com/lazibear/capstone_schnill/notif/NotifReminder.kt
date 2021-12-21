package com.lazibear.capstone_schnill.notif


import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.lazibear.capstone_schnill.R

class NotifReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_ID_CHANNEL)
            .setSmallIcon(R.drawable.ic_notif_schnill)
            .setContentTitle(intent.getStringExtra(NAME_EXTRA))
            .setContentText(intent.getStringExtra(NOTE_EXTRA))
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_ID_CHANNEL = "notification_daily_reminder"
        const val NAME_EXTRA = "name_extra"
        const val NOTE_EXTRA = "note_extra"


    }

}