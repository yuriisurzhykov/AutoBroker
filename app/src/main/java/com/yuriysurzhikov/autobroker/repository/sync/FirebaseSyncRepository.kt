package com.yuriysurzhikov.autobroker.repository.sync

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuriysurzhikov.autobroker.util.Const

class FirebaseSyncRepository {

    private val database = Firebase.firestore

    suspend fun fetchRegions(): List<DocumentSnapshot>? {
        val task = database.collection(Const.DictsConst.REGIONS_COLLECTION).get()
        return Tasks.await(task)?.documents
    }

    suspend fun fetchCarBrands(): List<DocumentSnapshot>? {
        val task = database.collection(Const.CarConst.CAR_COLLECTION).get()
        return Tasks.await(task)?.documents
    }

    suspend fun fetchGearboxTypes(): List<DocumentSnapshot>? {
        val task = database.collection(Const.DictsConst.GEAR_TYPE_COLLECTION).get()
        return Tasks.await(task)?.documents
    }

    suspend fun fetchFuelTypes(listener: EventListener<QuerySnapshot>) {
        database.collection(Const.DictsConst.FUEL_TYPE_COLLECTION).addSnapshotListener(listener)
    }
}