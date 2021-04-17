package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.local.FuelTypeCache
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class FuelTypeLocalMapper @Inject constructor() : IEntityMapper<FuelType, FuelTypeCache> {
    override fun mapFromEntity(entity: FuelType): FuelTypeCache {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: FuelTypeCache): FuelType {
        TODO("Not yet implemented")
    }

    override fun mapListFromEntity(entities: List<FuelType>): List<FuelTypeCache> {
        TODO("Not yet implemented")
    }

    override fun mapListToEntity(domains: List<FuelTypeCache>): List<FuelType> {
        TODO("Not yet implemented")
    }
}