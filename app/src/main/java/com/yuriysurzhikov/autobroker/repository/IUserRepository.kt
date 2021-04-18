package com.yuriysurzhikov.autobroker.repository

import com.yuriysurzhikov.autobroker.model.entity.User

interface IUserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun login(user: User)
    suspend fun logout(user: User)
}