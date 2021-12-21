package com.lazibear.capstone_schnill.ui.reminder

import androidx.lifecycle.ViewModel
import com.lazibear.capstone_schnill.data.reminder.Reminder
import com.lazibear.capstone_schnill.data.reminder.ReminderRepository

class ReminderViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {
    fun getAllReminder() = reminderRepository.getAllReminder()

    fun insertReminder(reminder: Reminder) = reminderRepository.insertReminder(reminder)

    fun deleteReminder(reminder: Reminder) = reminderRepository.deleteReminder(reminder)

    fun deleteAll() = reminderRepository.deleteAllReminder()
}