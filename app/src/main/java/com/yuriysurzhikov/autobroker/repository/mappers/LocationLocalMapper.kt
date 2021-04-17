package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper

class LocationLocalMapper : IEntityMapper<UserLocation?, UserLocationRoom?> {
    override fun mapFromEntity(entity: UserLocation?): UserLocationRoom? {
        return if (entity != null) {
            UserLocationRoom(0, entity.city, entity.region)
        } else null
    }

    override fun mapToEntity(domain: UserLocationRoom?): UserLocation? {
        return if (domain != null) {
            UserLocation(domain.city, domain.region)
        } else null
    }

    override fun mapListFromEntity(entities: List<UserLocation?>): List<UserLocationRoom?> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<UserLocationRoom?>): List<UserLocation?> {
        return domains.map { mapToEntity(it) }
    }
}