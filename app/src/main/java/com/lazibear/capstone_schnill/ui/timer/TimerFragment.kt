package com.lazibear.capstone_schnill.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.databinding.FragmentTimerBinding

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
        timerViewModel.setInitialTime(pomodoro)
        timerViewModel.currentTimeString.observe(viewLifecycleOwner, { binding.textViewCountdown.text = it })

        binding.fabStart.setOnClickListener {
            timerViewModel.startTimer() }

        binding.fabPause.setOnClickListener {}


        binding.fabStop.setOnClickListener {
            timerViewModel.resetTimer() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}