package com.lazibear.capstone_schnill.ui.timer

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.History
import com.lazibear.capstone_schnill.data.HistoryViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityMainBinding
import com.lazibear.capstone_schnill.ui.history.HistoryActivity
import com.lazibear.capstone_schnill.ui.history.HistoryViewModel
import com.lazibear.capstone_schnill.utils.BREAK_DURATION
import com.lazibear.capstone_schnill.utils.POMODORO_DURATION
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notify-channel"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var historyViewModel: HistoryViewModel


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedStateRegistry.isRestored
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        setTheme(R.style.Theme_Capstone_Schnill)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMenu()

        binding.btnSession.text = getString(R.string.session_name_focus)


        val timerViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val factory = HistoryViewModelFactory.getInstance(this)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]


        initPomod()
        var pomodSession = true
        timerViewModel.setInitialTime(POMODORO_DURATION)

        binding.btnSession.setOnClickListener {
            if (pomodSession) {
                initBreak()
                timerViewModel.setInitialTime(BREAK_DURATION)
                binding.btnSession.text = getString(R.string.session_name_break)
                pomodSession = false
            } else {
                initPomod()
                timerViewModel.setInitialTime(POMODORO_DURATION)
                binding.btnSession.setText(R.string.session_name_focus)
                pomodSession = true

            }

        }

        //observer
        timerViewModel.currentTimeString.observe(this, { binding.textViewCountdown.text = it })
        timerViewModel.progressBarCD.observe(this, { binding.progressCountdown.progress = it })
        timerViewModel.counterSession.observe(this, {
            if (it == null) {
                binding.tvSessionElapsed.text = getString(R.string.elapsed_session_null)
            } else binding.tvSessionElapsed.text = getString(R.string.text_session_elapsed) + it

        })



        timerViewModel.eventCountDownFinish.observe(this, {
            buttonState(false)
            binding.btnSession.isVisible = it
            binding.progressCountdown.progress = binding.progressCountdown.max
            showNotif()
        })

        binding.fabStart.setOnClickListener {
            timerViewModel.startTimer()
            buttonState(true)
            binding.btnSession.isVisible = false
            binding.mainToolbar.isVisible = false
        }

        binding.fabSave.setOnClickListener {

            val saveDialog = AlertDialog.Builder(this)
            val saveEditText = EditText(this)

            saveDialog.setMessage("Name your session")
                .setTitle("Do you want to save this session?")
                .setView(saveEditText)
                .setPositiveButton("Save") { _, id ->
                    val saveTitle =
                        saveEditText.text.toString() + getString(R.string.item_extra_text_session)
                    val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                    val elapsed = binding.tvSessionElapsed.text.toString()
                    val history =
                        History(session = saveTitle, date = date, elapsedSession = elapsed)
                    historyViewModel.insertHistory(history).also { finish() }

                }
                .setNegativeButton(R.string.cancel_alert) { _, id ->
                    val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
                    toast.show()
                }
            saveDialog.create()
            saveDialog.show()
        }

        binding.fabStop.setOnClickListener {
            val stopDialog = AlertDialog.Builder(this)
            stopDialog.setMessage("Do you want to stop the timer?")
                .setPositiveButton(R.string.stop_alert) { _, id ->
                    timerViewModel.resetTimer()
                    binding.progressCountdown.progress = 60 * 25
                    buttonState(false)
                    binding.btnSession.isVisible = true
                    binding.mainToolbar.isVisible = true


                }
                .setNegativeButton(R.string.cancel_alert) { _, id ->
                    val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
                    toast.show()
                }
            stopDialog.create()
            stopDialog.show()


        }


    }

    private fun buttonState(isRunning: Boolean) {
        binding.fabStart.isEnabled = !isRunning
        binding.mainToolbar.isEnabled = !isRunning
        binding.btnSession.isVisible = !isRunning
        binding.fabStop.isEnabled = isRunning

    }

    private fun showNotif() {
        val notificationManagerCompat =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
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
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notif = builder.build()
        notificationManagerCompat.notify(100, notif)

    }

    private fun initPomod() {
        binding.progressCountdown.max = 60 * 25
        binding.progressCountdown.progress = 60 * 25
    }

    private fun initBreak() {
        binding.progressCountdown.max = 60 * 1
        binding.progressCountdown.progress = 60 * 1
    }

    private fun initMenu() {
        binding.mainToolbar.inflateMenu(R.menu.main_menu)
        binding.mainToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.mainToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.mainToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.history -> {
                    val intent = Intent(this, HistoryActivity::class.java).apply { }
                    startActivity(intent)
                }
            }
            true
        }

    }


}






