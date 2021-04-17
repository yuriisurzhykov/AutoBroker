package com.yuriysurzhikov.autobroker.repository.core

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import com.yuriysurzhikov.autobroker.util.Const
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import kotlin.math.log

class SynchronizerImpl
@Inject
constructor(
    private val firebaseSyncRepository: FirebaseSyncRepository,
    private val localSyncRepository: LocalSyncRepository
) : ISynchronizer {

    private lateinit var job: Job

    private val TAG = SynchronizerImpl::class.simpleName

    override fun performSync() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e(TAG, "performSync: sync started")
            EventBus.getDefault().post(SyncStartEvent())
            syncRegions()
            syncGearBoxTypes()
        }
    }

    override fun cancelSync() {
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
        }
    }

    private fun syncRegions() {
        firebaseSyncRepository.fetchRegions(EventListener { value, error ->
            error?.printStackTrace()
            Log.e(
                TAG,
                "syncRegions: got regions ${value?.documents?.joinToString(separator = "\n") { it.id }}"
            )
            val totalList = mutableListOf<Region>()
            value?.documents?.forEach { document ->
                val region = Region(document.id, fetchLocalizations(document))
                totalList.add(region)
            }
            Log.e(TAG, "syncRegions: finish process result")
            CoroutineScope(Dispatchers.IO).launch {
                localSyncRepository.clearRegions()
                localSyncRepository.insertAllRegions(totalList)
                Log.e(TAG, "syncRegions: finish insert into DB")
                EventBus.getDefault().post(SyncSuccessEvent())
            }
        })
    }

    private fun syncGearBoxTypes() {
        firebaseSyncRepository.fetchGearboxTypes(EventListener { value, error ->
            Log.e(
                TAG,
                "syncGearBoxTypes: got gear types ${value?.documents?.joinToString(separator = "\n") { it.id }}"
            )
            error?.printStackTrace()
            val totalList = mutableListOf<GearboxType>()
            value?.documents?.forEach { document ->
                val region = GearboxType(document.id, fetchLocalizations(document))
                totalList.add(region)
            }
            Log.e(TAG, "syncGearBoxTypes: finish process gear types")
            CoroutineScope(Dispatchers.IO).launch {
                localSyncRepository.clearGearBoxTypes()
                localSyncRepository.insertAllGearTypes(totalList)
                Log.e(TAG, "syncGearBoxTypes: finish insert gear type into DB")
            }
        })
    }

    private fun fetchLocalizations(document: DocumentSnapshot): List<StringItem> {
        val labelRu = StringItem(Const.General.LABEL_RU, document[Const.General.LABEL_RU] as String)
        val labelEng =
            StringItem(Const.General.LABEL_EN, document[Const.General.LABEL_EN] as String)
        return listOf(labelRu, labelEng)
    }
}