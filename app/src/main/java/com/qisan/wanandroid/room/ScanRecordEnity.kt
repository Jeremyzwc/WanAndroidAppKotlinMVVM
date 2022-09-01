package com.qisan.wanandroid.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TableNameConstant.SCAN_RECORD_TABLE)
data class ScanRecordEnity(

    @ColumnInfo(name = "contentId")
    val contentId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "link")
    val link: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
