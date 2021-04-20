package com.yuriysurzhikov.autobroker.repository.core

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation

interface IUserLocalRepository {
    suspend fun getMainUser(): User?
    suspend fun getUser(id: String?): User?
    suspend fun getUserByLogin(email: String?): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun changeDisplayName(user: User, newName: String?)
    suspend fun changePhone(user: User, newPhone: String?)
    suspend fun changeProfileImage(user: User, newImage: Uri?)
    suspend fun changeLocationData(user: User, location: UserLocation)
    suspend fun checkUserExists(id: String): Boolean
    suspend fun logout(forAllPhones: Boolean)
    suspend fun login(user: User?)
    suspend fun register(user: User)
}