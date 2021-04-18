package com.yuriysurzhikov.autobroker.repository.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.util.Const
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserFirebaseRepository
@Inject
constructor(
    val userRemoteMapper: IEntityMapper<User, Map<String, Any?>>
) : IUserRepository {

    private val firestore = Firebase.firestore

    override suspend fun createUser(user: User) {
        val firebaseUser = userRemoteMapper.mapFromEntity(user)
        firestore.collection(Const.UserConst.USER_COLLECTION).add(firebaseUser)
    }

    override suspend fun updateUser(user: User) {
        var remoteUser: Map<String, Any?>? = null
        firestore.collection(Const.UserConst.USER_COLLECTION).document(user.strId)
            .update(userRemoteMapper.mapFromEntity(user))
    }

    override suspend fun login(user: User) {
        user.isLoggedIn = true
        user.fullRegistration = true
        updateUser(user)
    }

    override suspend fun logout(user: User) {
        user.isLoggedIn = false
        updateUser(user)
    }
}