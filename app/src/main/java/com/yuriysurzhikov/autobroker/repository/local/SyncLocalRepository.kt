package com.yuriysurzhikov.autobroker.repository.local

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.repository.ISyncRepository
import javax.inject.Inject

class SyncLocalRepository @Inject constructor(
    private val syncEntitiesDao: SyncEntitiesDao
) : ISyncRepository {

    override fun fetchRegions(): List<Region> {
        TODO("Not yet implemented")
    }

    override fun fetchCarBrands(): List<CarBrand> {
        TODO("Not yet implemented")
    }

    override fun fetchGearboxTypes(): List<GearboxType> {
        TODO("Not yet implemented")
    }

    override fun fetchFuelTypes(): List<FuelType> {
        TODO("Not yet implemented")
    }
}