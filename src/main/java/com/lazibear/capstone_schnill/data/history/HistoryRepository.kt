package com.lazibear.capstone_schnill.data.history

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(private val historyDao: HistoryDao, private val executor: ExecutorService) {

    companion object {


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

    //     fun insertHistory(history: History) = historyDatabase.historyDao().insertHistory(history)
    fun insertHistory(history: History) {
        executor.execute{
            historyDao.insertHistory(history)
        }
    }


    suspend fun deleteNote(history: History) = historyDao.deleteHistory(history)

    fun getAllHistory(): LiveData<List<History>> = historyDao.getAllHistory()
}