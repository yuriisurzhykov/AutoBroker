package com.yuriysurzhikov.autobroker.repository.sync

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.FirebaseStorage
import com.yuriysurzhikov.autobroker.model.entity.*
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncStartEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.model.local.CarRoom
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import com.yuriysurzhikov.autobroker.util.Const
import com.yuriysurzhikov.autobroker.util.IEntityMapper
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
    private val localSyncRepository: LocalSyncRepository,
    private val carMapper: IEntityMapper<Car, Map<String, Any>>,
    private val userRepositoryImpl: UserRepositoryImpl
) : ISynchronizer {

    private lateinit var job: Job

    private val TAG = SynchronizerImpl::class.simpleName

    @Synchronized
    override fun performSync(userId: String?) {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                EventBus.getDefault().post(SyncStartEvent())
                syncRegions()
                syncGearBoxTypes()
                syncFuelTypes()
                syncCars()
                syncUserData(userId)
                EventBus.getDefault().post(SyncSuccessEvent())
            } catch (e: Throwable) {
                EventBus.getDefault().post(SyncFailedEvent())
            }
        }
    }

    private suspend fun syncUserData(userId: String?) {
        if (!userId.isNullOrEmpty()) {
            val totalList = mutableListOf<Car>()
            firebaseSyncRepository.fetchUserCars(userId)?.forEach { document ->
                document.data?.let { docData ->
                    val car = carMapper.mapToEntity(docData)
                    totalList.add(car)
                }
            }
            userRepositoryImpl.clearAllCars()
            totalList.forEach {
                userRepositoryImpl.createCar(it)
            }
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

    private suspend fun syncFuelTypes() {
        val totalList = mutableListOf<FuelType>()
        firebaseSyncRepository.fetchFuelTypes()?.forEach { document ->
            val fuelType = FuelType(document.id, fetchLocalizations(document))
            totalList.add(fuelType)
        }
        localSyncRepository.clearFuelTypes()
        localSyncRepository.insertAllFuelTypes(totalList)
    }

    private suspend fun syncCars() {
        val totalCarList = mutableListOf<CarBrand>()
        firebaseSyncRepository.fetchCarBrands()?.forEach { document ->
            val totalModels = mutableListOf<CarModel>()
            firebaseSyncRepository.fetchCarModelsByBrand(document.id)?.forEach { modelDoc ->
                val model = CarModel(
                    modelDoc.id,
                    modelDoc[Const.CarConst.CAR_NAME_FIELD].toString(),
                    fetchIconUrl(modelDoc),
                    document.id
                )
                totalModels.add(model)
            }
            val car = CarBrand(
                document.id,
                document[Const.CarConst.CAR_NAME_FIELD].toString(),
                fetchIconUrl(document),
                totalModels
            )
            totalCarList.add(car)
        }
        localSyncRepository.clearCarBrands()
        localSyncRepository.insertAllCarBrands(totalCarList)
    }

    override fun syncOnlyUserData() {

    }

    override fun syncOnlyCars() {
        TODO("Not yet implemented")
    }

    private fun fetchIconUrl(document: DocumentSnapshot): String {
        val loadTask = FirebaseStorage.getInstance()
            .getReferenceFromUrl(document[Const.GeneralConst.DEFAULT_ICON_FIELD].toString())
            .downloadUrl
        return Tasks.await(loadTask).toString()
    }

    private fun fetchLocalizations(document: DocumentSnapshot): List<StringItem> {
        val labelRu =
            StringItem(
                Const.GeneralConst.LABEL_RU,
                document[Const.GeneralConst.LABEL_RU] as String?
            )
        val labelEng =
            StringItem(
                Const.GeneralConst.LABEL_EN,
                document[Const.GeneralConst.LABEL_EN] as String?
            )
        return listOf(labelRu, labelEng)
    }
}