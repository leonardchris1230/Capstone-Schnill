package com.lazibear.capstone_schnill.ui.timer

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.history.History
import com.lazibear.capstone_schnill.data.history.HistoryViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityMainBinding
import com.lazibear.capstone_schnill.ui.history.HistoryActivity
import com.lazibear.capstone_schnill.ui.history.HistoryViewModel
import com.lazibear.capstone_schnill.utils.BREAK_DURATION
import com.lazibear.capstone_schnill.utils.POMODORO_DURATION
import com.lazibear.capstone_schnill.utils.startAnimation
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
        supportActionBar?.customView = binding.mainToolbar
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
        timerViewModel.counterSession.observe(
            this,
            { binding.tvSessionElapsed.text = it.toString() })


        timerViewModel.eventCountDownFinish.observe(this, {
            buttonState(false)
            binding.btnSession.isVisible = it
            binding.progressCountdown.progress = binding.progressCountdown.max
            showNotif()
        })

        binding.fabStart.setOnClickListener {
            timerViewModel.startTimer()
            buttonState(true)
            binding.btnSession.isEnabled = false
        }

        binding.fabSave.setOnClickListener {
            initSave()
        }

        binding.fabStop.setOnClickListener {
            val stopDialog = Dialog(this)
            stopDialog.setContentView(R.layout.alert_timerstop)
            stopDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnYes = stopDialog.findViewById<TextView>(R.id.alert_STOP_YES)
            val btnCancel = stopDialog.findViewById<TextView>(R.id.alert_stop_cancel)


            btnYes.setOnClickListener {
                timerViewModel.resetTimer()
                binding.progressCountdown.progress = 60 * 25
                buttonState(false)
                binding.btnSession.isEnabled = true
                binding.mainToolbar.isVisible = true
                stopDialog.dismiss()
            }
            btnCancel.setOnClickListener {
                stopDialog.dismiss()
            }

            stopDialog.create()
            stopDialog.show()


        }


    }


    private fun buttonState(isRunning: Boolean) {
        binding.fabStart.isEnabled = !isRunning
        binding.mainToolbar.isEnabled = !isRunning
        binding.btnSession.isEnabled = !isRunning
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
        binding.progressCountdown.max = 60 * 5
        binding.progressCountdown.progress = 60 * 5
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

    private fun invisibleView() {
        binding.mainToolbar.isVisible = false
        binding.fabStart.isVisible = false
        binding.fabStop.isVisible = false
        binding.fabExplotion.isVisible = false
        binding.tvSessionElapsed.isVisible = false
        binding.textViewCountdown.isVisible = false
        binding.btnSession.isVisible = false
        binding.progressCountdown.isVisible = false
        binding.blueBg.isVisible = false
        binding.titleElapsed.isVisible = false

    }

    private fun initAnimExplotion() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fab_explotion_animation)
            .apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
            }
        binding.fabExplotion.startAnimation(animation) {
            binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.lime_schnill))
            Handler().postDelayed({
                finish()
            }, 500)

        }
    }

    private fun initSave() {
        val saveDialog = Dialog(this)
        saveDialog.setContentView(R.layout.alert_savingsessions)
        saveDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = saveDialog.findViewById<TextView>(R.id.alert_save_yes)
        val btnCancel = saveDialog.findViewById<TextView>(R.id.alert_save_cancel)
        val edtSave = saveDialog.findViewById<EditText>(R.id.edt_save_session)


        btnYes.setOnClickListener {
            val saveTitle =
                edtSave.text.toString() + getString(R.string.item_extra_text_session)
            val date =
                SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(Date())
            val elapsed = binding.tvSessionElapsed.text.toString()
            val history =
                History(session = saveTitle, date = date, elapsedSession = elapsed)
            historyViewModel.insertHistory(history).also {
                initAnimExplotion()
                invisibleView()
                val toast = Toast.makeText(this, "Session Saved!", Toast.LENGTH_LONG)
                toast.show()
                saveDialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
            toast.show()
            saveDialog.dismiss()
        }
        saveDialog.create()
        saveDialog.show()
    }


}







