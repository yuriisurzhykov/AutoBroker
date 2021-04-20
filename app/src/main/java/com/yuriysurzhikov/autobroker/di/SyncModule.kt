package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.local.FuelTypeCache
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.remote.UserFirebaseRepository
import com.yuriysurzhikov.autobroker.repository.sync.SynchronizerImpl
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SyncModule {

    @Provides
    @Singleton
    fun provideFirebaseSyncRepository(): FirebaseSyncRepository {
        return FirebaseSyncRepository()
    }

    @Provides
    @Singleton
    fun provideLocalSyncRepository(
        syncRegionDao: SyncDatabase,
        fuelEntityMapper: IEntityMapper<FuelType, FuelTypeCache>,
        regionsEntityMapper: IEntityMapper<Region, RegionRoom>,
        gearBoxEntityMapper: IEntityMapper<GearboxType, GearboxTypeCache>
    ): LocalSyncRepository {
        return LocalSyncRepository(
            syncRegionDao,
            fuelEntityMapper,
            regionsEntityMapper,
            gearBoxEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideSynchronizer(
        userRepositoryFirebase: UserFirebaseRepository,
        userRepositoryLocal: IUserLocalRepository,
        firebaseSyncRepository: FirebaseSyncRepository,
        localSyncRepository: LocalSyncRepository
    ): ISynchronizer {
        return SynchronizerImpl(
            userRepositoryFirebase,
            userRepositoryLocal,
            firebaseSyncRepository,
            localSyncRepository
        )
    }
}