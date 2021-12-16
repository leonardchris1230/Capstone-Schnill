package com.lazibear.capstone_schnill.data.reminder

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class Reminder {

    @Parcelize
    @Entity(tableName ="reminder_table")
    data class History(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Int = 0,

        @ColumnInfo(name = "session")
        @NonNull
        val session: String,


        @ColumnInfo(name = "date")
        @NonNull
        val date: String,

        @ColumnInfo(name = "elapsedSession")
        @NonNull
        val elapsedSession:String,

        ): Parcelable
}