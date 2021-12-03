package com.lazibear.capstone_schnill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.databinding.ActivityMainBinding
import com.lazibear.capstone_schnill.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var pomodSession = true
        val pomodoro = 25L
        binding.btnSession.setText(getString(R.string.session_name_focus))

        val timerViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        buttonState(false)

        binding.progressCountdown.max = 60 * 25
        binding.progressCountdown.progress = 60 * 25
        timerViewModel.setInitialTime(pomodoro)
        binding.btnSession.setOnClickListener {
            if (pomodSession) {
                val breakSession = 5L
                binding.progressCountdown.max = 60 * 5
                binding.progressCountdown.progress = 60 * 5
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
        timerViewModel.eventCountDownFinish.observe(this, { buttonState(!it) })
        timerViewModel.progressBarCD.observe(this, { binding.progressCountdown.progress = it })


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
        binding.fabStop.isEnabled = isRunning

    }


}






