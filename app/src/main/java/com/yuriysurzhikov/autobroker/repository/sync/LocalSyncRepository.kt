package com.yuriysurzhikov.autobroker.repository.sync

import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.mappers.CarBrandLocalMapper
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import javax.inject.Inject

class LocalSyncRepository @Inject constructor(
    syncDatabase: SyncDatabase,
    private val fuelEntityMapper: IEntityMapper<FuelType, FuelTypeCache>,
    private val regionsEntityMapper: IEntityMapper<Region, RegionRoom>,
    private val gearBoxEntityMapper: IEntityMapper<GearboxType, GearboxTypeCache>,
    private val carBrandLocalMapper: IEntityMapper<CarBrand, CarWithModelsRoom>
) : ISyncRepository {

    private val syncRegionsDao = syncDatabase.getRegionsDao()
    private val syncFuelTypesDao = syncDatabase.getFuelTypesDao()
    private val syncGearBoxTypeDao = syncDatabase.getGearBoxTypesDao()
    private val syncCarBrandDao = syncDatabase.getCarsDao()

    override suspend fun fetchRegions(): List<Region> {
        return syncRegionsDao.getAll().map {
            regionsEntityMapper.mapToEntity(it)
        }
    }

    override suspend fun fetchCarBrands(): List<CarBrand> {
        return emptyList()
    }

    override suspend fun fetchGearboxTypes(): List<GearboxType> {
        return syncGearBoxTypeDao.getAll().map {
            gearBoxEntityMapper.mapToEntity(it)
        }
    }

    override suspend fun fetchFuelTypes(): List<FuelType> {
        return syncFuelTypesDao.getAll().map {
            fuelEntityMapper.mapToEntity(it)
        }
    }


    suspend fun insertAllRegions(items: List<Region>) {
        syncRegionsDao.addAll(items.map {
            regionsEntityMapper.mapFromEntity(it)
        })
    }

    suspend fun insertAllGearTypes(items: List<GearboxType>) {
        syncGearBoxTypeDao.addAll(items.map {
            gearBoxEntityMapper.mapFromEntity(it)
        })
    }

    suspend fun insertAllFuelTypes(items: List<FuelType>) {
        syncFuelTypesDao.addAll(
            items.map {
                fuelEntityMapper.mapFromEntity(it)
            }
        )
    }

    suspend fun clearRegions() {
        syncRegionsDao.clearRegions()
    }

    suspend fun clearGearBoxTypes() {
        syncGearBoxTypeDao.clearGearBoxTypes()
    }

    suspend fun clearFuelTypes() {
        syncFuelTypesDao.clearFuelTypes()
    }

    suspend fun clearCarBrands() {
        syncCarBrandDao.clearCarModelTable()
        syncCarBrandDao.clearBrandTable()
    }

    suspend fun insertAllCarBrands(brands: List<CarBrand>) {
        val mappedBrands = carBrandLocalMapper.mapListFromEntity(brands)
        mappedBrands.forEach { brandWithCar ->
            syncCarBrandDao.add(brandWithCar.carBrand)
            brandWithCar.carModels.forEach {
                syncCarBrandDao.addCarModel(it)
            }
        }
    }
}