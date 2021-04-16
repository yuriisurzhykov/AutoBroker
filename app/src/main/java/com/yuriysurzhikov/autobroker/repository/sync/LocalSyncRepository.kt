package com.yuriysurzhikov.autobroker.repository.sync

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(
    private val syncEntitiesDao: SyncEntitiesDao
) : ISyncRepository {

    override suspend fun fetchRegions(): List<Region> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCarBrands(): List<CarBrand> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchGearboxTypes(): List<GearboxType> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFuelTypes(): List<FuelType> {
        TODO("Not yet implemented")
    }


    suspend fun insertAllRegions(items: List<Region>) {

    }

    suspend fun insertAllGearTypes(items: List<GearboxType>) {

    }

    suspend fun insertAllFuelTypes(items: List<FuelType>) {

    }

    suspend fun clearRegions() {
        syncEntitiesDao.
    }

    suspend fun clearGearBoxTypes() {

    }

    suspend fun clearFuelTypes() {

    }
}