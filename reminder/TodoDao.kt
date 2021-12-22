package com.lazibear.capstone_schnill.data.reminder

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Query("SELECT * FROM reminder ")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo):Long

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("delete from reminder")
    fun deleteAllHistory()
}