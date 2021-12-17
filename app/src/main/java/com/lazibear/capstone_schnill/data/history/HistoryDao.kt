package com.lazibear.capstone_schnill.data.history

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HistoryDao {

//    @RawQuery(observedEntities = [History::class])
//    fun getHistory(query: SupportSQLiteQuery): DataSource.Factory<Int, History>

//    @Query("select * from history_table where id = :habitId")
//    fun getHistorybyId(habitId: Int): LiveData<History>

    @Query("SELECT * FROM history_table ")
    fun getAllHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History):Long

    @Delete
    fun deleteHistory(habits: History)


}