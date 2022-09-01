package com.qisan.wanandroid.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ScanRecordEnity::class], version = 1, exportSchema = false)
abstract class WanDB : RoomDatabase(){

    abstract fun getScanRecordDao(): ScanRecordDao

    companion object {
        @Volatile
        private var instantce: WanDB? = null
        private const val DB_NAME = "wan_android.db"

        fun getInstance(context: Context): WanDB? {
            if (instantce == null) {
                synchronized(WanDB::class.java) {
                    if (instantce == null) {
                        instantce = createInstance(context)
                    }
                }
            }
            return instantce
        }

        private fun createInstance(context: Context): WanDB {
            return Room.databaseBuilder(context.applicationContext, WanDB::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}