package com.lazibear.capstone_schnill.data.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [History::class], version = 1,exportSchema = false)

abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

//    companion object {
//        private const val DB_NAME = "history_database.db"
//        @Volatile private var instance: HistoryDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            HistoryDatabase::class.java,
//            DB_NAME
//        ).build()
//    }

    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}