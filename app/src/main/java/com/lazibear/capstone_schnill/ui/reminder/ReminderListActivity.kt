package com.lazibear.capstone_schnill.ui.reminder

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.reminder.ReminderViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityReminderListBinding

class ReminderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderListBinding
    private lateinit var reminderViewModel: ReminderViewModel
    private lateinit var factory: ReminderViewModelFactory
    private lateinit var reminderAdapter: ReminderAdapter

    private lateinit var fabAddSchedule: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setTheme(R.style.Theme_Capstone_Schnill)

        binding = ActivityReminderListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMenu()


        factory = ReminderViewModelFactory.getInstance(this)
        reminderViewModel = ViewModelProvider(this, factory)[ReminderViewModel::class.java]
        reminderAdapter = ReminderAdapter()

        binding.rvReminder.setHasFixedSize(true)
        binding.rvReminder.layoutManager = LinearLayoutManager(this)
        binding.rvReminder.adapter = reminderAdapter




        reminderViewModel.getAllReminder().observe(this, {
            reminderAdapter.submitList(it)
            if (it.isEmpty()) {
                binding.nullReminder.visibility = View.VISIBLE

            }

        })
        setFabClick()
    }


    private fun setFabClick() {

        fabAddSchedule = findViewById(R.id.fab)
        fabAddSchedule.setOnClickListener {
            val addIntent = Intent(this, ReminderActivity::class.java)
            startActivity(addIntent)
        }
    }

    private fun initMenu() {

        binding.reminderToolbar.setNavigationIcon(R.drawable.ic_back_arrow_black)
        binding.reminderToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.reminderToolbar.inflateMenu(R.menu.reminder_menu)

        binding.reminderToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_deleteAll -> {
                    initDelete()
                }
            }
            true
        }


    }

    private fun initDelete() {
        val stopDialog = Dialog(this)
        stopDialog.setContentView(R.layout.alert_reminder_deleteall)
        stopDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnYes = stopDialog.findViewById<TextView>(R.id.alert_del_yes_reminder)
        val btnCancel = stopDialog.findViewById<TextView>(R.id.alert_del_cancel_reminder)

        btnYes.setOnClickListener {
            reminderViewModel.deleteAll()
            val toast = Toast.makeText(this, "Reminder cleared", Toast.LENGTH_SHORT)
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