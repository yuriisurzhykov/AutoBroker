package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.model.local.CarModelRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class CarModelLocalMapper @Inject constructor() : IEntityMapper<CarModel, CarModelRoom> {
    override fun mapFromEntity(entity: CarModel): CarModelRoom {
        return CarModelRoom(entity.id, entity.name, entity.imageSrc, entity.carBrandId)
    }

    override fun mapToEntity(domain: CarModelRoom): CarModel {
        return CarModel(domain.id, domain.name, domain.iconUrl, domain.carBrandId)
    }

    override fun mapListFromEntity(entities: List<CarModel>): List<CarModelRoom> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<CarModelRoom>): List<CarModel> {
        return domains.map { mapToEntity(it) }
    }

    private fun extractCarBrand(carModelId: String): String {
        val splitted = carModelId.split("_")
        return if (splitted.isNotEmpty()) "brand_${splitted[0]}" else "unknown"
    }
}