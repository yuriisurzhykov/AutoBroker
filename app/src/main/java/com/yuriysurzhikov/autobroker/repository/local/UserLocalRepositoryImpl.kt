package com.yuriysurzhikov.autobroker.repository.local

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.User
import com.yuriysurzhikov.autobroker.model.UserLocation
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import javax.inject.Inject

class UserLocalRepositoryImpl : IUserRepository {

    @Inject
    lateinit var userLocalDao: UserLocalDao

    override fun createUser(user: User) {

    }

    override fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun changeDisplayName(user: User, newName: String?) {
        TODO("Not yet implemented")
    }

    override fun changePhone(user: User, newPhone: String?) {
        TODO("Not yet implemented")
    }

    override fun changeProfileImage(user: User, newImage: Uri?) {
        TODO("Not yet implemented")
    }

    override fun changeLocationData(user: User, location: UserLocation) {
        TODO("Not yet implemented")
    }
}