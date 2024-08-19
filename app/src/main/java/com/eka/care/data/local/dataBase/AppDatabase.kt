package com.eka.care.data.local.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eka.care.data.local.dao.UserDao
import com.eka.care.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}