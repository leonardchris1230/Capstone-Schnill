package com.lazibear.capstone_schnill.data.reminder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.ui.reminder.ReminderViewModel

class ReminderViewModelFactory(private val reminderRepository: ReminderRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ReminderViewModelFactory? = null

        fun getInstance(context: Context): ReminderViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ReminderViewModelFactory(
                    ReminderRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ReminderViewModel::class.java) -> {
                ReminderViewModel(reminderRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}