package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.local.CarRoom
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class CarMapper @Inject constructor() : IEntityMapper<Car, CarRoom> {
    override fun mapFromEntity(entity: Car): CarRoom {
        return CarRoom(
            name = entity.name,
            brandId = entity.brandId,
            modelId = entity.modelId,
            cost = entity.cost,
            description = entity.description,
            mileage = entity.mileage,
            carYearIssue = entity.carYearIssue,
            regionNumber = entity.carNumber,
            imagesUri = entity.images
        )
    }

    override fun mapToEntity(domain: CarRoom): Car {
        return Car(
            domain.id,
            domain.name,
            domain.brandId,
            domain.modelId,
            domain.regionNumber,
            domain.carYearIssue,
            domain.mileage,
            domain.description,
            domain.cost,
            domain.imagesUri
        )
    }

    override fun mapListFromEntity(entities: List<Car>): List<CarRoom> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<CarRoom>): List<Car> {
        return domains.map { mapToEntity(it) }
    }
}