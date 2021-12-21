package com.lazibear.capstone_schnill.data.reminder

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.ui.reminder.todo.TodoViewModel

class TodoViewModelFactory (private val todoRepository: TodoRepository): ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: TodoViewModelFactory? = null

        fun getInstance(context: Context): TodoViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TodoViewModelFactory(
                    TodoRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(TodoViewModel::class.java) -> {
                TodoViewModel(todoRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}