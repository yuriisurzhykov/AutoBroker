package com.yuriysurzhikov.autobroker.repository.mappers

import android.net.Uri
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class UserLocalMapper @Inject constructor() : IEntityMapper<User?, UserRoom?> {

    override fun mapFromEntity(entity: User?): UserRoom? {
        return if (entity != null) {
            UserRoom(
                entity.strId,
                "",
                entity.phone,
                entity.email,
                entity.displayName,
                entity.photoUrl.toString(),
                0
            )
        } else null
    }

    override fun mapToEntity(domain: UserRoom?): User? {
        return if (domain != null) {
            User(
                domain.userId,
                domain.displayName,
                domain.phone,
                domain.email,
                Uri.parse(domain.photoUrl),
                null
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