package com.yuriysurzhikov.autobroker.repository.local

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    val localDatabase: LocalDatabase,
    val localMapper: IEntityMapper<User?, UserRoom?>
) : IUserRepository {

    override fun getMainUser(): User? {
        val roomUser = localDatabase.userRepository().getFirstUser()
        return localMapper.mapToEntity(roomUser)
    }

    override fun getUser(id: String?): User? {
        val userLocal = localDatabase.userRepository().getUserBy(id)
        return localMapper.mapToEntity(userLocal)
    }

    override fun createUser(user: User) {
        val roomUser = localMapper.mapFromEntity(user)!!
        localDatabase.userRepository().create(roomUser)
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

    override fun checkUserExists(id: String): Boolean {
        return getUser(id) != null
    }
}