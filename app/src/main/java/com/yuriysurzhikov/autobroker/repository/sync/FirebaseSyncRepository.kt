package com.yuriysurzhikov.autobroker.repository.sync

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.model.entity.*
import com.yuriysurzhikov.autobroker.util.Const

class FirebaseSyncRepository {

    private val database = Firebase.firestore

    suspend fun fetchRegions(): List<Region> {
        val totalList = mutableListOf<Region>()
        database.collection(Const.Dicts.REGIONS_COLLECTION).addSnapshotListener { value, error ->
            value?.documents?.forEach { document ->
                val region = Region(document.id, fetchLocalizations(document))
                totalList.add(region)
            }
        }
        return totalList
    }

    suspend fun fetchCarBrands(): List<CarBrand> {
        database.collection(Const.Car.CAR_COLLECTION).addSnapshotListener { value, error ->
            value?.documents?.forEach { document ->
                document
            }
        }
        return emptyList()
    }

    suspend fun fetchGearboxTypes(): List<GearboxType> {
        val totalList = mutableListOf<GearboxType>()
        database.collection(Const.Dicts.REGIONS_COLLECTION).addSnapshotListener { value, error ->
            value?.documents?.forEach { document ->
                val region = GearboxType(document.id, fetchLocalizations(document))
                totalList.add(region)
            }
        }
        return totalList
    }

    suspend fun fetchFuelTypes(): List<FuelType> {
        val totalList = mutableListOf<FuelType>()
        database.collection(Const.Dicts.REGIONS_COLLECTION).addSnapshotListener { value, error ->
            value?.documents?.forEach { document ->
                val region = FuelType(document.id, fetchLocalizations(document))
                totalList.add(region)
            }
        }
        return totalList
    }

    private fun fetchLocalizations(document: DocumentSnapshot): List<StringItem> {
        val labelRu = StringItem(Const.General.LABEL_RU, document[Const.General.LABEL_RU] as String)
        val labelEng = StringItem(Const.General.LABEL_EN, document[Const.General.LABEL_EN] as String)
        return listOf(labelRu, labelEng)
    }
}