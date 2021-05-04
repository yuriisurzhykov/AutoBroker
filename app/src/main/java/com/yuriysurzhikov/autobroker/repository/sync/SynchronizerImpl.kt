package com.yuriysurzhikov.autobroker.repository.sync

import com.google.firebase.firestore.DocumentSnapshot
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SynchronizerImpl
@Inject
constructor(
    private val firebaseSyncRepository: FirebaseSyncRepository,
    private val localSyncRepository: LocalSyncRepository
) : ISynchronizer {

    private lateinit var job: Job

    private val TAG = SynchronizerImpl::class.simpleName

    @Synchronized
    override fun performSync() {
        CoroutineScope(Dispatchers.IO).launch {
            EventBus.getDefault().post(SyncStartEvent())
            syncRegions()
            syncGearBoxTypes()
            EventBus.getDefault().post(SyncSuccessEvent())
        }
    }

    override fun cancelSync() {
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
        }
    }

    private suspend fun syncRegions() {
        val totalList = mutableListOf<Region>()
        firebaseSyncRepository.fetchRegions()?.forEach { document ->
            val region = Region(document.id, fetchLocalizations(document))
            totalList.add(region)
        }
        localSyncRepository.clearRegions()
        localSyncRepository.insertAllRegions(totalList)
    }

    private suspend fun syncGearBoxTypes() {
        val totalList = mutableListOf<GearboxType>()
        firebaseSyncRepository.fetchGearboxTypes()?.forEach { document ->
            val region = GearboxType(document.id, fetchLocalizations(document))
            totalList.add(region)
        }
        localSyncRepository.clearGearBoxTypes()
        localSyncRepository.insertAllGearTypes(totalList)
    }

    private fun fetchLocalizations(document: DocumentSnapshot): List<StringItem> {
        val labelRu =
            StringItem(Const.GeneralConst.LABEL_RU, document[Const.GeneralConst.LABEL_RU] as String)
        val labelEng =
            StringItem(Const.GeneralConst.LABEL_EN, document[Const.GeneralConst.LABEL_EN] as String)
        return listOf(labelRu, labelEng)
    }
}