package com.lazibear.capstone_schnill.ui.history

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
            if (it.isEmpty()) {
                binding.nullRv.visibility = View.VISIBLE
            }
        })


    }

    private fun initMenu() {

        binding.historyToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.historyToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.historyToolbar.inflateMenu(R.menu.history_menu)

        binding.historyToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteAll -> {
                    initDelete()
//                    val stopDialog = AlertDialog.Builder(this)
//                    stopDialog.setMessage("This action will clear all your history, are you sure?")
//                        .setPositiveButton("YES") { _, _ ->
//                            historyViewModel.deleteHistory()
//                            finish()
//                        }
//                        .setNegativeButton(R.string.cancel_alert) { _, _ ->
//                            val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
//                            toast.show()
//                        }
//                    stopDialog.create()
//                    stopDialog.show()

                }
            }
            true
        }


    }

    private fun initDelete() {
        val stopDialog = Dialog(this)
        stopDialog.setContentView(R.layout.alert_deletehistory)
        stopDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = stopDialog.findViewById<TextView>(R.id.alert_del_yes)
        val btnCancel = stopDialog.findViewById<TextView>(R.id.alert_del_cancel)


        btnYes.setOnClickListener {
            historyViewModel.deleteHistory()
            val toast = Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT)
            toast.show()
            finish()
            stopDialog.dismiss()
        }
        btnCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT)
            toast.show()
            stopDialog.dismiss()
        }

        stopDialog.create()
        stopDialog.show()
    }
}