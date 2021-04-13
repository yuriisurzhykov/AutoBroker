package com.yuriysurzhikov.autobroker.repository.local

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.repository.IUserRepository

class UserLocalRepositoryImpl : IUserRepository {

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