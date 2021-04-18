package com.yuriysurzhikov.autobroker.repository.local

import android.net.Uri
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.remote.UserFirebaseRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val firebaseRepository: UserFirebaseRepository,
    val localDatabase: LocalDatabase,
    val localMapper: IEntityMapper<User?, UserRoom?>,
    val locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>,
    val userRemoteMapper: IEntityMapper<User, Map<String, Any?>>
) : IUserLocalRepository {

    private val userRepository = localDatabase.userRepository()

    override suspend fun getMainUser(): User? {
        val roomUser = userRepository.getFirstUser()
        return localMapper.mapToEntity(roomUser)
    }

    override suspend fun getUser(id: String?): User? {
        val userRemote = firebaseRepository.getUser(id)
        val userLocal = userRepository.getUserBy(id)
        if (userLocal == null && userRemote != null) {
            val userRoom = localMapper.mapFromEntity(userRemote)
            localDatabase.userRepository().add(userRoom!!)
            return localMapper.mapToEntity(userRoom)
        }
        return localMapper.mapToEntity(userLocal)
    }

    override suspend fun getUserByLogin(email: String?): User? {
        val userLocal = userRepository.getUserByEmail(email)
        return localMapper.mapToEntity(userLocal)
    }

    override suspend fun createUser(user: User) {
        val roomUser = localMapper.mapFromEntity(user)!!
        userRepository.add(roomUser)
        firebaseRepository.createUser(user)
    }

    override suspend fun updateUser(user: User) {
        val roomUser = localMapper.mapFromEntity(user)
        if (roomUser != null) {
            userRepository.createLocation(locationMapper.mapFromEntity(user.location)!!)
            roomUser.locationId = userRepository
                .getLocationByRegionAndCity(user.location.region, user.location.city).locationId
            userRepository.update(roomUser)
            firebaseRepository.updateUser(user)
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

    override suspend fun logout() {
        val userRoom = localDatabase.userRepository().getFirstUser()
        if (userRoom != null) {
            userRoom.isLogged = false
            localDatabase.userRepository().update(userRoom)
        }
    }

    override suspend fun login(user: User?) {
        if (user != null) {
            user.isLoggedIn = true
            val userRoom = localMapper.mapFromEntity(user)
            if (userRoom != null) {
                localDatabase.userRepository().update(userRoom)
                firebaseRepository.updateUser(user)
            }
        }
    }

    override suspend fun register(user: User) {
        user.isLoggedIn = true
        user.fullRegistration = true
        val userRoom = localMapper.mapFromEntity(user)
        if (userRoom != null) {
            localDatabase.userRepository().update(userRoom)
            firebaseRepository.updateUser(user)
        }
    }
}