package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.local.FuelTypeCache
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class FuelTypeLocalMapper @Inject constructor() : IEntityMapper<FuelType, FuelTypeCache> {
    override fun mapFromEntity(entity: FuelType): FuelTypeCache {
        return FuelTypeCache(0, entity.externalId, entity.nameLocalization)
    }

    override fun mapToEntity(domain: FuelTypeCache): FuelType {
        return FuelType(domain.serverId, domain.name)
    }

    override fun mapListFromEntity(entities: List<FuelType>): List<FuelTypeCache> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<FuelTypeCache>): List<FuelType> {
        return domains.map { mapToEntity(it) }
    }
}