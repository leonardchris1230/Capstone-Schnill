package com.lazibear.capstone_schnill.data.reminder

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "reminder")
data class Reminder(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,

    @ColumnInfo(name = "title")
    @NonNull
    var title: String,

    @ColumnInfo(name = "note")
    @NonNull
    var note: String,

    @ColumnInfo(name = "date")
    @NonNull
    var date: String,

    @ColumnInfo(name = "time")
    @NonNull
    var time: String,
) : Parcelable
