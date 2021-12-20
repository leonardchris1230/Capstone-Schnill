package com.lazibear.capstone_schnill.ui.choice

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.databinding.ActivityChoiceBinding
import com.lazibear.capstone_schnill.ui.about.AboutActivity
import com.lazibear.capstone_schnill.ui.reminder.ReminderActivity
import com.lazibear.capstone_schnill.ui.reminder.todo.TodoActivity
import com.lazibear.capstone_schnill.ui.timer.MainActivity

class ChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoiceBinding
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Capstone_Schnill)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHomeTitle.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java).apply {  }
            startActivity(intent)
        }

        binding.btnTimer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {  }
            startActivity(intent)}
        binding.btnReminder.setOnClickListener {
            val intent = Intent (this, TodoActivity::class.java).apply {  }
            startActivity(intent)
        }

    }
}
