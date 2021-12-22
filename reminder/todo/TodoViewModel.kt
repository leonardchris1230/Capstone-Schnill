package com.lazibear.capstone_schnill.ui.reminder.todo

import androidx.lifecycle.ViewModel
import com.lazibear.capstone_schnill.data.reminder.Todo
import com.lazibear.capstone_schnill.data.reminder.TodoRepository

class TodoViewModel (private val todoRepository: TodoRepository) : ViewModel(){

    fun getAllTodo() = todoRepository.getAllTodo()

    fun insertTodo(todo: Todo) = todoRepository.insertTodo(todo)

    fun deleteTodo(todo: Todo) = todoRepository.deleteTodo(todo)

}