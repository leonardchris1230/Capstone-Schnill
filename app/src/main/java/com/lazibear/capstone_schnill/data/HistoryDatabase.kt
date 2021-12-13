package com.lazibear.capstone_schnill.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
        companion object{
            @Volatile
            private var INSTANCE: HistoryDatabase? = null

            fun getInstance(context: Context): HistoryDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java, "history_table.db")
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