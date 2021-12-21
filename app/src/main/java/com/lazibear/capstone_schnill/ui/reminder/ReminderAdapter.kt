package com.lazibear.capstone_schnill.ui.reminder

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazibear.capstone_schnill.data.reminder.Reminder
import com.lazibear.capstone_schnill.databinding.ItemReminderListBinding

class ReminderAdapter :
    ListAdapter<Reminder, ReminderAdapter.ReminderViewHolder>(DiffUtilNote()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemReminder =
            ItemReminderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(itemReminder)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    fun getReminderAt(position: Int): Reminder {
        return getItem(position)
    }

    class ReminderViewHolder(private val binding: ItemReminderListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(reminder: Reminder) {
            with(binding) {
                tvTodoTitle.text = reminder.title
                tvTodoDate.text = reminder.date
                tvClock.text = reminder.time
                tvNoteReminder.text = reminder.note

            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return newItem == oldItem
        }
    }


}