package com.lazibear.capstone_schnill.data.history

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HistoryDao {


    @Query("SELECT * FROM history_table ")
    fun getAllHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History): Long

    @Delete
    fun deleteHistory(habits: History)

    @Query("delete from history_table")
    fun deleteAllHistory()


}