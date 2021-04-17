package com.yuriysurzhikov.autobroker.repository.local

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    val localDatabase: LocalDatabase,
    val syncDatabase: SyncDatabase,
    val localMapper: IEntityMapper<User?, UserRoom?>,
    val locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>
) : IUserRepository {

    private val userRepository = localDatabase.userRepository()

    override suspend fun getMainUser(): User? {
        val roomUser = userRepository.getFirstUser()
        return localMapper.mapToEntity(roomUser)
    }

    override suspend fun getUser(id: String?): User? {
        val userLocal = userRepository.getUserBy(id)
        return localMapper.mapToEntity(userLocal)
    }

    override suspend fun getUserByLogin(email: String?): User? {
        val userLocal = userRepository.getUserByEmail(email)
        return localMapper.mapToEntity(userLocal)
    }

    override suspend fun createUser(user: User) {
        val roomUser = localMapper.mapFromEntity(user)!!
        userRepository.add(roomUser)
    }

    override suspend fun updateUser(user: User) {
        val roomUser = localMapper.mapFromEntity(user)
        if (roomUser != null) {
            userRepository.createLocation(locationMapper.mapFromEntity(user.location)!!)
            roomUser.locationId = userRepository
                .getLocationByRegionAndCity(user.location.region, user.location.city).locationId
            userRepository.update(roomUser)
        }
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun changeDisplayName(user: User, newName: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun changePhone(user: User, newPhone: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun changeProfileImage(user: User, newImage: Uri?) {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocationData(user: User, location: UserLocation) {
        TODO("Not yet implemented")
    }

    override suspend fun checkUserExists(id: String): Boolean {
        return getUser(id) != null
    }
}