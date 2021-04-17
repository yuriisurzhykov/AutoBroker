package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class GearBoxTypeLocalMapper @Inject constructor() : IEntityMapper<GearboxType, GearboxTypeCache> {
    override fun mapFromEntity(entity: GearboxType): GearboxTypeCache {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: GearboxTypeCache): GearboxType {
        TODO("Not yet implemented")
    }

    override fun mapListFromEntity(entities: List<GearboxType>): List<GearboxTypeCache> {
        TODO("Not yet implemented")
    }

    override fun mapListToEntity(domains: List<GearboxTypeCache>): List<GearboxType> {
        TODO("Not yet implemented")
    }
}