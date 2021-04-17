package com.yuriysurzhikov.autobroker.repository.core

import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class SynchronizerImpl
@Inject
constructor(
    private val firebaseSyncRepository: FirebaseSyncRepository,
    private val localSyncRepository: LocalSyncRepository
) : ISynchronizer {

    private lateinit var job: Job

    override fun performSync() {
        if (!this::job.isInitialized) {
            job = CoroutineScope(Dispatchers.IO).launch {
                EventBus.getDefault().post(SyncStartEvent())

                val remoteRegions = firebaseSyncRepository.fetchRegions()
                localSyncRepository.clearRegions()
                localSyncRepository.insertAllRegions(remoteRegions)

                val remoteGearboxType = firebaseSyncRepository.fetchGearboxTypes()
                localSyncRepository.clearGearBoxTypes()
                localSyncRepository.insertAllGearTypes(remoteGearboxType)

                val remoteFuelType = firebaseSyncRepository.fetchFuelTypes()
                localSyncRepository.clearFuelTypes()
                localSyncRepository.insertAllFuelTypes(remoteFuelType)

                EventBus.getDefault().post(SyncSuccessEvent())
            }
        }
        if (job.isActive) {
            cancelSync()
        }
        job.start()
    }

    override fun cancelSync() {
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
        }
    }
}