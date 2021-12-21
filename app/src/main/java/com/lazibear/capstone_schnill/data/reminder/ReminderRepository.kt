package com.lazibear.capstone_schnill.data.reminder

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReminderRepository(
    private val reminderDao: ReminderDao,
    private val executors: ExecutorService
) {

    fun getAllReminder(): LiveData<List<Reminder>> = reminderDao.getAllReminder()

    fun insertReminder(reminder: Reminder) {
        executors.execute {
            reminderDao.insertReminder(reminder)
        }
    }

    fun deleteAllReminder() {
        executors.execute { reminderDao.deleteAllList() }
    }

    fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)

    companion object {

        @Volatile
        private var instance: ReminderRepository? = null

        fun getInstance(context: Context): ReminderRepository {
            return instance ?: synchronized(this) {
                if (ReminderRepository.instance == null) {
                    val database = ReminderDatabase.getInstance(context)
                    ReminderRepository.instance = ReminderRepository(
                        database.reminderDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as ReminderRepository
            }

        }
    }
}
