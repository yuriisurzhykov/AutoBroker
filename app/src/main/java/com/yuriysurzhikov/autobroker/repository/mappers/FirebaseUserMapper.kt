package com.yuriysurzhikov.autobroker.repository.mappers

import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.util.DatabaseUtils
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class FirebaseUserMapper @Inject constructor() : IEntityMapper<FirebaseUser, User> {

    override fun mapFromEntity(entity: FirebaseUser): User {
        return User(
            entity.uid,
            "",
            entity.displayName ?: DatabaseUtils.generateRandomUserName(),
            entity.phoneNumber,
            entity.email,
            entity.photoUrl,
            UserLocation.empty(),
            fullRegistration = false,
            isLoggedIn = false
        )
    }

    @Throws(IllegalAccessException::class)
    override fun mapToEntity(domain: User): FirebaseUser {
        throw IllegalAccessException("Cannot be implemented!")
    }

    override fun mapListFromEntity(entities: List<FirebaseUser>): List<User> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<User>): List<FirebaseUser> {
        return domains.map { mapToEntity(it) }
    }
}