package com.yuriysurzhikov.autobroker.repository.mappers

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.utils.LocationConverter
import com.yuriysurzhikov.autobroker.util.Const.UserConst
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserRemoteMapper
@Inject
constructor(
    val locationConverter: LocationConverter
) : IEntityMapper<User, Map<String, Any?>> {
    override fun mapFromEntity(entity: User): Map<String, Any?> {
        return mapOf(
            UserConst.ID to entity.strId,
            UserConst.PASSWORD to entity.password,
            UserConst.DISPLAY_NAME to entity.displayName,
            UserConst.EMAIL to entity.email.orEmpty(),
            UserConst.PHONE to entity.phone.orEmpty(),
            UserConst.PHOTO_URL to entity.photoUrl.toString(),
            UserConst.LOCATION to locationConverter.convertFromLocation(entity.location),
            UserConst.IS_LOGGED_IN to entity.isLoggedIn,
            UserConst.IS_FULL_REGISTER to entity.fullRegistration
        )
    }

    override fun mapToEntity(domain: Map<String, Any?>): User {
        return User(
            domain[UserConst.ID] as String,
            domain[UserConst.PASSWORD] as String,
            domain[UserConst.DISPLAY_NAME] as String,
            domain[UserConst.EMAIL] as String?,
            domain[UserConst.PHONE] as String?,
            Uri.parse("${domain[UserConst.PHOTO_URL]}"),
            locationConverter.convertToLocation(domain[UserConst.LOCATION] as String),
            domain[UserConst.IS_LOGGED_IN] as Boolean,
            domain[UserConst.IS_FULL_REGISTER] as Boolean
        )
    }

    override fun mapListFromEntity(entities: List<User>): List<Map<String, Any?>> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<Map<String, Any?>>): List<User> {
        return domains.map { mapToEntity(it) }
    }
}