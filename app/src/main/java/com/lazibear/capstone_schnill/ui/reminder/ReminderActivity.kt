package com.lazibear.capstone_schnill.ui.reminder

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.reminder.Reminder
import com.lazibear.capstone_schnill.data.reminder.ReminderViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityReminderBinding
import com.lazibear.capstone_schnill.notif.NotifReminder
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NAME_EXTRA
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTE_EXTRA
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTIFICATION_ID
import com.lazibear.capstone_schnill.notif.NotifReminder.Companion.NOTIFICATION_ID_CHANNEL
import java.util.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var reminderViewModel: ReminderViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Capstone_Schnill)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reminderToolbar.setNavigationIcon(R.drawable.ic_back_arrow_black)
        binding.reminderToolbar.setNavigationOnClickListener { onBackPressed() }


        val factory = ReminderViewModelFactory.getInstance(this)
        reminderViewModel = ViewModelProvider(this, factory)[ReminderViewModel::class.java]


        binding.btnSubmitReminder.setOnClickListener {
            if (binding.addEdRemindName.text?.isEmpty() == true) {
                val toast =
                    Toast.makeText(this, "Please enter the reminder name", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                createNotification()
                reminderNotification()


            }

        }

    }

    private fun reminderNotification() {
        val intent = Intent(applicationContext, NotifReminder::class.java)
        val remindName = binding.addEdRemindName.text.toString()
        val note = binding.addEdRemindNote.text.toString()
        intent.putExtra(NAME_EXTRA, remindName)
        intent.putExtra(NOTE_EXTRA, note)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                applicationContext,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            TODO("VERSION.SDK_INT < M")
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
        showAlert(time, remindName, note)

    }

    private fun showAlert(time: Long, remindName: String, note: String) {

        val saveDialog = Dialog(this)
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
        saveDialog.setContentView(R.layout.alert_reminder)
        saveDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = saveDialog.findViewById<TextView>(R.id.alert_reminder_save_yes)
        val btnCancel = saveDialog.findViewById<TextView>(R.id.alert_reminder_save_cancel)
        val tvTitle = saveDialog.findViewById<TextView>(R.id.alert_reminder_tvtitle)
        val tvDate = saveDialog.findViewById<TextView>(R.id.alert_reminder_tvdate)
        val tvTime = saveDialog.findViewById<TextView>(R.id.alert_reminder_tvtime)

        tvTitle.text = remindName
        tvTime.text = timeFormat.format(date)
        tvDate.text = dateFormat.format(date)


        btnYes.setOnClickListener {
            saveInit(time, remindName, note)
            saveDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
            toast.show()
            saveDialog.dismiss()
        }
        saveDialog.create()
        saveDialog.show()


    }

    private fun saveInit(time: Long, remindName: String, note: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        val reminder = Reminder(
            title = remindName,
            note = note,
            date = dateFormat.format(date),
            time = timeFormat.format(date)
        )
        reminderViewModel.insertReminder(reminder).also {
            val toast = Toast.makeText(this, "Reminder Saved!", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }


    }

    private fun getTime(): Long {
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

    private fun createNotification() {
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