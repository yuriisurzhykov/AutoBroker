package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class RegionsLocalMapper @Inject constructor() : IEntityMapper<Region, RegionRoom> {
    override fun mapFromEntity(entity: Region): RegionRoom {
        return RegionRoom(0, entity.externalId, entity.localizations)
    }

    override fun mapToEntity(domain: RegionRoom): Region {
        return Region(domain.remoteId, domain.name)
    }

    override fun mapListFromEntity(entities: List<Region>): List<RegionRoom> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<RegionRoom>): List<Region> {
        return domains.map { mapToEntity(it) }
    }
}