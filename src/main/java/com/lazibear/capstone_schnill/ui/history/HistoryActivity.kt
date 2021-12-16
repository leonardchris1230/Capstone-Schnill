package com.lazibear.capstone_schnill.ui.history

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.history.HistoryViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var factory: HistoryViewModelFactory
    private lateinit var historyAdapter: HistoryAdapter


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setTheme(R.style.Theme_Capstone_Schnill)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMenu()


        factory = HistoryViewModelFactory.getInstance(this)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
        historyAdapter = HistoryAdapter()

        binding.historyRV.setHasFixedSize(true)
        binding.historyRV.layoutManager = LinearLayoutManager(this)
        binding.historyRV.adapter = historyAdapter

        historyViewModel.getAllHistory().observe(this, {
            historyAdapter.submitList(it)
        })


    }

    private fun initMenu() {

        binding.historyToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.historyToolbar.setNavigationOnClickListener { onBackPressed() }


    }
}