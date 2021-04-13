package com.yuriysurzhikov.autobroker.repository.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.repository.ISyncRepository
import com.yuriysurzhikov.autobroker.util.Const

class FirebaseSyncRepository : ISyncRepository {

    override fun fetchRegions(): List<Region> {
        return emptyList()
    }

    override fun fetchCarBrands(): List<CarBrand> {
        database.collection("car")
        database.collection(Const.Car.CAR_COLLECTION).get().result?.documents?.forEach {
            val brandName = it.getString(FIELD_NAME)
        }
        return emptyList()
    }

    override fun fetchGearboxTypes(): List<GearboxType> {
        return emptyList()
    }

    override fun fetchFuelTypes(): List<FuelType> {
        return emptyList()
    }

    companion object {
        private val database = Firebase.firestore
        private const val FIELD_NAME = "name"
    }
}