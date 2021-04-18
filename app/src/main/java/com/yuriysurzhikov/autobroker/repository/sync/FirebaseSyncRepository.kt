package com.yuriysurzhikov.autobroker.repository.sync

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.util.Const

class FirebaseSyncRepository {

    private val database = Firebase.firestore

    fun fetchRegions(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.DictsConst.REGIONS_COLLECTION).addSnapshotListener(listener)
    }

    fun fetchCarBrands(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.CarConst.CAR_COLLECTION).addSnapshotListener(listener)
    }

    fun fetchGearboxTypes(eventListener: EventListener<QuerySnapshot>) {
        database.collection(Const.DictsConst.GEAR_TYPE_COLLECTION).addSnapshotListener(eventListener)
    }

    fun fetchFuelTypes(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.DictsConst.FUEL_TYPE_COLLECTION).addSnapshotListener(listener)
    }
}