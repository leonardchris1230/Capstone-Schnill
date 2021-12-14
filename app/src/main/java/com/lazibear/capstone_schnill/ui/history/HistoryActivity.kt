package com.lazibear.capstone_schnill.ui.history

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.HistoryDatabase
import com.lazibear.capstone_schnill.data.HistoryRepository
import com.lazibear.capstone_schnill.data.HistoryViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyDatabase: HistoryDatabase
    private lateinit var repository: HistoryRepository
    private lateinit var factory: HistoryViewModelFactory
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setTheme(R.style.Theme_Capstone_Schnill)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)





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
}