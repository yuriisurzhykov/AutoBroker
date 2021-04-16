package com.yuriysurzhikov.autobroker.repository.sync

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region

interface ISyncRepository {
    suspend fun fetchRegions(): List<Region>
    suspend fun fetchCarBrands(): List<CarBrand>
    suspend fun fetchGearboxTypes(): List<GearboxType>
    suspend fun fetchFuelTypes(): List<FuelType>
}