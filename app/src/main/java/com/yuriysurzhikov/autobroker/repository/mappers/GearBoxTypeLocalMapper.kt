package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class GearBoxTypeLocalMapper @Inject constructor() : IEntityMapper<GearboxType, GearboxTypeCache> {
    override fun mapFromEntity(entity: GearboxType): GearboxTypeCache {
        return GearboxTypeCache(0, entity.externalId, entity.nameLocalizations)
    }

    override fun mapToEntity(domain: GearboxTypeCache): GearboxType {
        return GearboxType(domain.serverId, domain.name)
    }

    override fun mapListFromEntity(entities: List<GearboxType>): List<GearboxTypeCache> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<GearboxTypeCache>): List<GearboxType> {
        return domains.map { mapToEntity(it) }
    }
}