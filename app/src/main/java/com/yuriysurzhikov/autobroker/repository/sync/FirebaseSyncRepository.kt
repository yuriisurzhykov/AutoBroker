package com.yuriysurzhikov.autobroker.repository.sync

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.model.entity.*
import com.yuriysurzhikov.autobroker.util.Const

class FirebaseSyncRepository {

    private val database = Firebase.firestore

    fun fetchRegions(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.Dicts.REGIONS_COLLECTION).addSnapshotListener(listener)
    }

    fun fetchCarBrands(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.Car.CAR_COLLECTION).addSnapshotListener(listener)
    }

    fun fetchGearboxTypes(eventListener: EventListener<QuerySnapshot>) {
        database.collection(Const.Dicts.GEAR_TYPE_COLLECTION).addSnapshotListener(eventListener)
    }

    fun fetchFuelTypes(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.Dicts.FUEL_TYPE_COLLECTION).addSnapshotListener(listener)
    }
}