package com.lazibear.capstone_schnill.ui.history

import androidx.lifecycle.ViewModel
import com.lazibear.capstone_schnill.data.history.History
import com.lazibear.capstone_schnill.data.history.HistoryRepository

class HistoryViewModel (
    private val repository: HistoryRepository
): ViewModel() {

    fun insertHistory(history: History) = repository.insertHistory(history)

    fun getAllHistory() = repository.getAllHistory()
}