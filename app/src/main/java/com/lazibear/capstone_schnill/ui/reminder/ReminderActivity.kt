package com.lazibear.capstone_schnill.ui.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.databinding.ActivityReminderBinding
import com.lazibear.capstone_schnill.notif.NotifReminder
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NAME_EXTRA
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTE_EXTRA
import java.util.*
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTIFICATION_ID
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTIFICATION_ID_CHANNEL

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding : ActivityReminderBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        createNotification()
        binding.btnSubmitReminder.setOnClickListener{ reminderNotification()}
    }
    private fun reminderNotification(){
        val intent = Intent(applicationContext, NotifReminder::class.java)
        val remindName = binding.addEdRemindName.text.toString()
        val note = binding.addEdRemindNote.text.toString()
        intent.putExtra(NAME_EXTRA, remindName)
        intent.putExtra(NOTE_EXTRA, note)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
        showAlert( time, remindName, note)

    }

    private fun showAlert(time: Long, remindName: String, note: String) {

        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Reminder")
            .setMessage(
                "Reminder Name: " + remindName +
                        "\nNote: " + note +
                        "\nAt: "+ dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Okay"){_,_ ->}
            .show()

    }

    private fun getTime() : Long{
        val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.pickerTime.minute
        } else {
            TODO("VERSION.SDK_INT < M")
        }
        val hour = binding.pickerTime.hour
        val day = binding.pickerDate.dayOfMonth
        val month = binding.pickerDate.month
        val year = binding.pickerDate.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis


    }

    private fun createNotification(){
        val name = "Reminder Notification"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notifChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(NOTIFICATION_ID_CHANNEL, name, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notifChannel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notifChannel)


    }
}