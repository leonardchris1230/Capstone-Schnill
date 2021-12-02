package com.lazibear.capstone_schnill.ui.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.databinding.FragmentTimerBinding
import kotlin.math.log

class TimerFragment : Fragment() {


    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View {
       val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)


        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val pomodoro = 25L
        val progresspomodoro = 25
        binding.progressCountdown.max = 60*25
        binding.progressCountdown.progress = 60*25
        timerViewModel.setInitialTime(pomodoro)
        timerViewModel.setProgress(progresspomodoro)
        timerViewModel.currentTimeString.observe(viewLifecycleOwner, {
            binding.textViewCountdown.text = it
//            val currentProgress = timerViewModel.currentProgress
//            if (currentProgress != null) {
//                binding.progressCountdown.progress = 100 - currentProgress
//            }
        })
        timerViewModel.progressBarCD.observe(viewLifecycleOwner,{
            binding.progressCountdown.progress = it
        })



        binding.fabStart.setOnClickListener {
            timerViewModel.startTimer()
       buttonState(true)}

        binding.fabPause.setOnClickListener {}


        binding.fabStop.setOnClickListener {
            timerViewModel.resetTimer()
            buttonState(false)}

        return root
    }

    private fun buttonState(isRunning: Boolean) {
        binding.fabStart.isEnabled = !isRunning
        binding.fabStop.isEnabled = isRunning

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}