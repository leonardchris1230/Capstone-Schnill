package com.lazibear.capstone_schnill.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import javax.sql.DataSource


@Dao
interface HistoryDao {

//    @RawQuery(observedEntities = [History::class])
//    fun getHistory(query: SupportSQLiteQuery): DataSource.Factory<Int, History>

    @Query("select * from history_table where id = :habitId")
    fun getHistorybyId(habitId: Int): LiveData<History>

    @Insert
    fun insert(habit: History): Long

    @Insert
    fun insertAll(vararg history: History)

    @Delete
    fun delete(habits: History)


}