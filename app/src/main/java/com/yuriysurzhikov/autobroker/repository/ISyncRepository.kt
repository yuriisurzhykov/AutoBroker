package com.yuriysurzhikov.autobroker.repository

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region

interface ISyncRepository {
    fun fetchRegions(): List<Region>
    fun fetchCarBrands(): List<CarBrand>
    fun fetchGearboxTypes(): List<GearboxType>
    fun fetchFuelTypes(): List<FuelType>
}