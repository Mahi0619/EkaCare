package com.eka.care

import android.app.Application
import androidx.room.Room
import com.eka.care.data.local.dataBase.AppDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application(){
    companion object DatabaseSetup {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        MyApplication.database =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").build()
    }
}
