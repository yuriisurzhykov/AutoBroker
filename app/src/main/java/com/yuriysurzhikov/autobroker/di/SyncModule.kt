package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.model.entity.*
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.sync.SynchronizerImpl
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.remote.UserFirebaseRepository
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
        gearBoxEntityMapper: IEntityMapper<GearboxType, GearboxTypeCache>,
        carBrandEntityMapper: IEntityMapper<CarBrand, CarWithModelsRoom>
    ): LocalSyncRepository {
        return LocalSyncRepository(
            syncRegionDao,
            fuelEntityMapper,
            regionsEntityMapper,
            gearBoxEntityMapper,
            carBrandEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideSynchronizer(
        userRepositoryFirebase: UserFirebaseRepository,
        userRepositoryLocal: IUserLocalRepository,
        firebaseSyncRepository: FirebaseSyncRepository,
        localSyncRepository: LocalSyncRepository,
        carMapper: IEntityMapper<Car, Map<String, Any>>,
        userRepositoryImpl: UserRepositoryImpl
    ): ISynchronizer {
        return SynchronizerImpl(
            firebaseSyncRepository,
            localSyncRepository,
            carMapper,
            userRepositoryImpl
        )
    }
}