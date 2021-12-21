package com.lazibear.capstone_schnill.ui.reminder.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazibear.capstone_schnill.data.reminder.Todo
import com.lazibear.capstone_schnill.databinding.ItemTodoBinding

class TodoAdapter() :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(DiffUtilNote()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder{
        val itemTodo = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(itemTodo)
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    fun getTodoAt(position: Int): Todo {
        return getItem(position)
    }

    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(todo: Todo) {
            with(binding) {
                tvTodoTitle.text = todo.title
                tvTodoDate.text = todo.date


            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return newItem == oldItem
        }
    }



}