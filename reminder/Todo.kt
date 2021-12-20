package com.lazibear.capstone_schnill.data.reminder

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "reminder")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    var id: Int = 0,
    var title: String,
    var note: String,
    var priority: String,
    var date: String
) : Parcelable