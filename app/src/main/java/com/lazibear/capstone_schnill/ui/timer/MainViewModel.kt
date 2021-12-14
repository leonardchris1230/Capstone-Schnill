package com.lazibear.capstone_schnill.ui.timer

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lazibear.capstone_schnill.data.HistoryRepository

class MainViewModel(): ViewModel() {

    private var timer: CountDownTimer? = null

    private val initialTime = MutableLiveData<Long>()
    private val currentTime = MutableLiveData<Long>()
    private var currentProgressTime = MutableLiveData<Int>()
    private val _sessionCounter = MutableLiveData<Int>()


    // The String version of the current time (hh:mm:ss)
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time / 1000)
    }

    val progressBarCD = currentProgressTime

    fun getInitialTime() = initialTime.value.toString()

    // Event which triggers the end of count down
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish


    var mSessionCounter:Int = 0

    val counterSession = _sessionCounter


    fun setInitialTime(minuteFocus: Long) {
        val initialTimeMillis = minuteFocus * 60 * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis
        currentProgressTime.value = initialTimeMillis.toInt()*60


        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                currentProgressTime.value = millisUntilFinished.toInt()/1000


            }

            override fun onFinish( ) {
                mSessionCounter++
                _sessionCounter.value = mSessionCounter
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
    fun sessionCounter(){

    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    fun showNotif() {



    }


}


