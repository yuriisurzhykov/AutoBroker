package com.yuriysurzhikov.autobroker.repository.mappers

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.local.UserLocalDao
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserLocalMapper @Inject constructor(
    private val userLocalDao: UserLocalDao,
    private val locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>
) : IEntityMapper<User?, UserRoom?> {

    override fun mapFromEntity(entity: User?): UserRoom? {
        return if (entity != null) {
            UserRoom(
                entity.strId,
                "",
                entity.email,
                entity.phone,
                entity.displayName,
                entity.photoUrl.toString(),
                0,
                entity.fullRegistration,
                entity.isLoggedIn
            )
        } else null
    }

    override fun mapToEntity(domain: UserRoom?): User? {
        return if (domain != null) {
            val location =
                locationMapper.mapToEntity(userLocalDao.getLocationById(domain.locationId))
            User(
                domain.userId,
                domain.displayName,
                domain.phone,
                domain.email,
                Uri.parse(domain.photoUrl),
                location ?: UserLocation.empty(),
                domain.fullRegistration,
                domain.isLogged
            )
        } else null
    }

    override fun mapListFromEntity(entities: List<User?>): List<UserRoom?> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<UserRoom?>): List<User?> {
        return domains.map { mapToEntity(it) }
    }
}