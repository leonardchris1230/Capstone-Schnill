package com.lazibear.capstone_schnill.data.reminder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder ORDER by id DESC")
    fun getAllReminder(): LiveData<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder): Long

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("delete from reminder")
    fun deleteAllList()
}