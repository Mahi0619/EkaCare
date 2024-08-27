package com.eka.care.data.repository

import com.eka.care.data.local.dao.UserDao
import com.eka.care.data.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }
}
