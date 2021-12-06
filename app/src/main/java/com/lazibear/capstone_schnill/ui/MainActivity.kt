package com.lazibear.capstone_schnill.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT)
        setTheme(R.style.Theme_Capstone_Schnill)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var pomodSession = true
        val pomodoro = 25L
        binding.btnSession.setText(getString(R.string.session_name_focus))

        val timerViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.progressCountdown.max = 60 * 25
        binding.progressCountdown.progress = 60 * 25
        timerViewModel.setInitialTime(pomodoro)
        binding.btnSession.setOnClickListener {
            if (pomodSession) {
                val breakSession = 1L
                binding.progressCountdown.max = 60 * 1
                binding.progressCountdown.progress = 60 * 1
                timerViewModel.setInitialTime(breakSession)
                binding.btnSession.setText(getString(R.string.session_name_break))
                pomodSession = false


            } else {
                val pomodoroSession = 25L
                binding.progressCountdown.max = 60 * 25
                binding.progressCountdown.progress = 60 * 25
                timerViewModel.setInitialTime(pomodoroSession)
                binding.btnSession.setText(R.string.session_name_focus)
                pomodSession = true
            }

        }


        timerViewModel.currentTimeString.observe(this, { binding.textViewCountdown.text = it })

        timerViewModel.progressBarCD.observe(this, { binding.progressCountdown.progress = it })


        timerViewModel.eventCountDownFinish.observe(this,{ buttonState(false)
        binding.btnSession.isVisible = it
        binding.progressCountdown.progress = binding.progressCountdown.max
            showNotif()

        })


        binding.fabStart.setOnClickListener {
            timerViewModel.startTimer()
            buttonState(true)
            binding.btnSession.isVisible = false
        }

        binding.fabPause.setOnClickListener {

        }


        binding.fabStop.setOnClickListener {
            timerViewModel.resetTimer()
            binding.progressCountdown.progress = 60 * 25
            buttonState(false)
            binding.btnSession.isVisible = true
        }


    }

    private fun buttonState(isRunning: Boolean) {
        binding.fabStart.isEnabled = !isRunning
        binding.btnSession.isVisible = !isRunning
        binding.fabStop.isEnabled = isRunning

    }

    private fun showNotif() {
        val notificationManagerCompat =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = TaskStackBuilder.create(this).run { addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notif_schnill)
            .setContentTitle("Congrats!")
            .setContentText("Your Session is over!")
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "channelName",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)}
        val notif = builder.build()
        notificationManagerCompat.notify(100, notif)

    }
     override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.navigation_timer ->{
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                true
            }
            R.id.navigation_history ->{
                val i = Intent(this, HistoryFragment::class.java)
                startActivity(i)
                true
            }
            R.id.navigation_notifications ->{
                val i = Intent(this, NotificationsFragment::class.java)
                startActivity(i)
                true
            }
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
     
     companion object {
        const val NOTIFICATION_CHANNEL_ID = "notify-channel"
    }



}






