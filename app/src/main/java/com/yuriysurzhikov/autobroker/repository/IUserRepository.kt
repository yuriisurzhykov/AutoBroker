package com.yuriysurzhikov.autobroker.repository

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation

interface IUserRepository {
    fun getMainUser(): User?
    fun getUser(id: String?): User?
    suspend fun createUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun changeDisplayName(user: User, newName: String?)
    fun changePhone(user: User, newPhone: String?)
    fun changeProfileImage(user: User, newImage: Uri?)
    fun changeLocationData(user: User, location: UserLocation)
    fun checkUserExists(id: String): Boolean
}