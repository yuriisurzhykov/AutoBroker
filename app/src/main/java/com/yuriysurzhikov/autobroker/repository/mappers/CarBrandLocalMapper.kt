package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.model.local.CarBrandRoom
import com.yuriysurzhikov.autobroker.model.local.CarModelRoom
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class CarBrandLocalMapper
@Inject
constructor(
    private val carModelMapper: IEntityMapper<CarModel, CarModelRoom>
) : IEntityMapper<CarBrand, CarWithModelsRoom> {

    override fun mapFromEntity(entity: CarBrand): CarWithModelsRoom {
        return CarWithModelsRoom(
            CarBrandRoom(entity.name, entity.iconUrl, entity.id),
            carModelMapper.mapListFromEntity(entity.models)
        )
    }

    override fun mapToEntity(domain: CarWithModelsRoom): CarBrand {
        return CarBrand(
            domain.carBrand.id,
            domain.carBrand.name,
            domain.carBrand.iconUrl,
            carModelMapper.mapListToEntity(domain.carModels)
        )
    }

    override fun mapListFromEntity(entities: List<CarBrand>): List<CarWithModelsRoom> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<CarWithModelsRoom>): List<CarBrand> {
        return domains.map { mapToEntity(it) }
    }
}