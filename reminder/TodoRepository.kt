package com.lazibear.capstone_schnill.data.reminder

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TodoRepository(private val todoDao: TodoDao, private val executors: ExecutorService) {

    fun getAllTodo(): LiveData<List<Todo>> = todoDao.getAllTodo()

    fun insertTodo(todo: Todo){
        executors.execute{
            todoDao.insertTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)
    companion object{

        @Volatile
        private var instance: TodoRepository?= null

        fun getInstance(context: Context): TodoRepository {
            return instance ?: synchronized(this) {
                if (TodoRepository.instance == null) {
                    val database = TodoDatabase.getInstance(context)
                    TodoRepository.instance = TodoRepository(
                        database.todoDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return TodoRepository.instance as TodoRepository
            }

        }
    }
}