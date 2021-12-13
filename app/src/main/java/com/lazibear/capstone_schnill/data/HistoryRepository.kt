package com.lazibear.capstone_schnill.data

import android.content.Context
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(private val historyDao: HistoryDao, private val executor: ExecutorService) {

    companion object {

        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true
        @Volatile
        private var instance: HistoryRepository? = null

        fun getInstance(context: Context): HistoryRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = HistoryDatabase.getInstance(context)
                    instance = HistoryRepository(
                        database.historyDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as HistoryRepository
            }

        }

    }
    fun insertHabit(newHistory: History) {
        executor.execute{
            historyDao.insert(newHistory)
        }
    }


}