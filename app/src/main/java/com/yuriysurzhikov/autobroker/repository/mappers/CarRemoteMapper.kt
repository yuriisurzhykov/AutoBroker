package com.yuriysurzhikov.autobroker.repository.mappers

import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.repository.utils.ImagesListConverter
import com.yuriysurzhikov.autobroker.repository.utils.RegionNumberConverter
import com.yuriysurzhikov.autobroker.util.Const
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class CarRemoteMapper @Inject constructor(
    private val converter: RegionNumberConverter,
    private val imagesListConverter: ImagesListConverter
) : IEntityMapper<Car, Map<String, Any>> {
    override fun mapFromEntity(entity: Car): Map<String, Any> {
        return mapOf(
            Const.CarConst.CAR_ID to entity.id,
            Const.CarConst.CAR_TITLE to entity.name,
            Const.CarConst.CAR_BRAND_ID to entity.brandId,
            Const.CarConst.CAR_MODEL_ID to entity.modelId,
            Const.CarConst.CAR_NUMBER to converter.convertToString(entity.carNumber),
            Const.CarConst.CAR_YEAR_ISSUE to entity.carYearIssue,
            Const.CarConst.CAR_IMAGES_REFS to imagesListConverter.convertToString(entity.images),
            Const.CarConst.CAR_MILEAGE to entity.mileage,
            Const.CarConst.CAR_DESCRIPTION to entity.description,
            Const.CarConst.CAR_PRICE to entity.cost
        )
    }

    override fun mapToEntity(domain: Map<String, Any>): Car {
        val id = domain[Const.CarConst.CAR_ID] as String
        val title = domain[Const.CarConst.CAR_TITLE] as String
        val carBrandId = domain[Const.CarConst.CAR_BRAND_ID] as String
        val carModelId = domain[Const.CarConst.CAR_MODEL_ID] as String
        val carNumber = converter.convertFromString((domain[Const.CarConst.CAR_NUMBER] as String))
        val yearIssue = domain[Const.CarConst.CAR_YEAR_ISSUE] as String
        val imagesUrl =
            imagesListConverter.convertFromString(domain[Const.CarConst.CAR_IMAGES_REFS] as String)
        val mileage = domain[Const.CarConst.CAR_MILEAGE] as Double
        val description = domain[Const.CarConst.CAR_DESCRIPTION] as String
        val price = domain[Const.CarConst.CAR_PRICE] as Double
        return Car(
            id,
            title,
            carBrandId,
            carModelId,
            carNumber,
            yearIssue,
            mileage,
            description,
            price,
            imagesUrl
        )
    }

    override fun mapListFromEntity(entities: List<Car>): List<Map<String, Any>> {
        return entities.map { mapFromEntity(it) }
    }

    override fun mapListToEntity(domains: List<Map<String, Any>>): List<Car> {
        return domains.map { mapToEntity(it) }
    }
}