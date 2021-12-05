package com.lazibear.capstone_schnill.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lazibear.capstone_schnill.R

class MainViewModel: ViewModel() {

    private var timer: CountDownTimer? = null

    private val initialTime = MutableLiveData<Long>()
    private val currentTime = MutableLiveData<Long>()
    private var currentProgressTime = MutableLiveData<Int>()
    private var sessionState = MutableLiveData<Boolean>()

    // The String version of the current time (hh:mm:ss)
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time / 1000)
    }

    val progressBarCD = currentProgressTime
//    val currentProgress = Transformations.map(currentProgressTime){ time ->
//        TimeUnit.SECONDS(time / 100)
//    }


    fun getInitialTime() = initialTime.value.toString()

    // Event which triggers the end of count down
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish



    fun setInitialTime(minuteFocus: Long) {
        val initialTimeMillis = minuteFocus * 60 * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis
        currentProgressTime.value = initialTimeMillis.toInt()*60


        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                currentProgressTime.value = millisUntilFinished.toInt()/1000
                Log.d("mylog","check "+currentTime.value)

                /*    currentProgressTime.value = (minuteFocus * 60 - millisUntilFinished) / 100*/

            }

            override fun onFinish( ) {
                Log.d("mylog","fucking done")
                showNotif()
                resetTimer()
            }
        }
    }

    fun startTimer() {
        timer?.start()
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime.value = initialTime.value
        _eventCountDownFinish.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    fun showNotif() {



    }


}