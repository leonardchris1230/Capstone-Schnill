package com.lazibear.capstone_schnill.data.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lazibear.capstone_schnill.ui.history.HistoryViewModel

class HistoryViewModelFactory(
    private val repository: HistoryRepository
) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null

        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(
                    HistoryRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}

//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        try {
//            val constructor = modelClass.getDeclaredConstructor(HistoryRepository::class.java)
//            return constructor.newInstance(repository)
//        } catch (e: Exception) {
//            Log.e("THIS ERROR", "ERROR -> ${e.message.toString()}")
//        }
//        return super.create(modelClass)
//    }
