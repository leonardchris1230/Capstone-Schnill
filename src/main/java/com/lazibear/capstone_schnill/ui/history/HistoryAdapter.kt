package com.lazibear.capstone_schnill.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.History
import com.lazibear.capstone_schnill.databinding.ItemHistoryBinding


class HistoryAdapter() :
    ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffUtilNote()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemHistory =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(itemHistory)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item)
    }

    fun getHistoryAt(position: Int): History {
        return getItem(position)
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(history: History) {
            with(binding) {
                tvHistoryName.text = history.session
                historyDate.text = history.date
                elapsedSessionHistory.text = history.elapsedSession
            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return newItem == oldItem
        }
    }
}