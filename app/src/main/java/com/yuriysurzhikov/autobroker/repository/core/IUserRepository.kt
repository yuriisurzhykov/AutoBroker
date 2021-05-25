package com.yuriysurzhikov.autobroker.repository.core

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.local.CarRoom

interface IUserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun login(user: User)
    suspend fun logout(user: User)
    suspend fun uploadFile(userId: String, stringUri: Uri): String
    suspend fun createCarForUser(userId: String, carRoom: Car): String?
}