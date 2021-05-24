package com.yuriysurzhikov.autobroker.repository.remote

import android.net.Uri
import androidx.core.net.toFile
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.local.CarRoom
import com.yuriysurzhikov.autobroker.repository.core.IUserRepository
import com.yuriysurzhikov.autobroker.util.Const
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import javax.inject.Inject

class UserFirebaseRepository
@Inject
constructor(
    val userRemoteMapper: IEntityMapper<User, Map<String, Any?>>,
    val carMapper: IEntityMapper<Car, Map<String, Any>>
) : IUserRepository {

    private val firestore = Firebase.firestore

    suspend fun getUser(id: String?): User? {
        val userTask = firestore.collection(Const.UserConst.USER_COLLECTION)
            .document(id ?: "")
            .get()
        val value = Tasks.await(userTask)
        if (value != null && value.data != null) {
            return userRemoteMapper.mapToEntity(value.data!!)
        }
        return null
    }

    override suspend fun createUser(user: User) {
        val firebaseUser = userRemoteMapper.mapFromEntity(user)
        firestore.collection(Const.UserConst.USER_COLLECTION).document(user.strId).set(firebaseUser)
    }

    override suspend fun updateUser(user: User) {
        firestore.collection(Const.UserConst.USER_COLLECTION)
            .document(user.strId)
            .set(userRemoteMapper.mapFromEntity(user), SetOptions.merge())
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

    override suspend fun createCarForUser(userId: String, carRoom: Car): Unit =
        withContext(Dispatchers.IO) {
            val entity = carMapper.mapFromEntity(carRoom)
            firestore
                .collection(Const.UserConst.USER_COLLECTION)
                .document(userId)
                .collection(Const.CarConst.USER_CARS_COLLECTION)
                .document(carRoom.id)
                .set(entity, SetOptions.merge())
            return@withContext
        }

    override suspend fun uploadFile(userId: String, stringUri: Uri): String {
        val fireStorage = FirebaseStorage.getInstance()
        val ref = fireStorage.reference
            .child(Const.UserConst.USER_COLLECTION)
            .child(userId)
            .child(File.createTempFile(userId, ".jpg").name)
        val task = ref.putFile(stringUri)
        Tasks.await(task)
        return Tasks.await(ref.downloadUrl).toString()
    }
}