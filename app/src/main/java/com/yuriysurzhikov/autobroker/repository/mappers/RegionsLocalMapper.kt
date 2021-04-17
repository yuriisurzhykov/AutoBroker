package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class RegionsLocalMapper @Inject constructor(): IEntityMapper<Region, RegionRoom> {
    override fun mapFromEntity(entity: Region): RegionRoom {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: RegionRoom): Region {
        TODO("Not yet implemented")
    }

    override fun mapListFromEntity(entities: List<Region>): List<RegionRoom> {
        TODO("Not yet implemented")
    }

    override fun mapListToEntity(domains: List<RegionRoom>): List<Region> {
        TODO("Not yet implemented")
    }
}