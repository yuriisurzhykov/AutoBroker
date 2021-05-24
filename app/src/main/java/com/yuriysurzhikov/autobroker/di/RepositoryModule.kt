package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.CarRoom
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.database.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.mappers.CarMapper
import com.yuriysurzhikov.autobroker.repository.mappers.UserLocalMapper
import com.yuriysurzhikov.autobroker.repository.remote.UserFirebaseRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideIUserRepository(
        firebaseRepository: UserFirebaseRepository,
        localDatabase: LocalDatabase,
        syncDatabase: SyncDatabase,
        userMapper: UserLocalMapper,
        locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>,
        userRemoteMapper: IEntityMapper<User, Map<String, Any?>>,
        carMapper: IEntityMapper<Car, CarRoom>
    ): IUserLocalRepository {
        return UserRepositoryImpl(
            firebaseRepository,
            localDatabase,
            userMapper,
            locationMapper,
            userRemoteMapper,
            carMapper
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseUserRepository(
        userMapper: IEntityMapper<User, Map<String, Any?>>,
        carMapper: IEntityMapper<Car, Map<String, Any>>
    ): UserFirebaseRepository {
        return UserFirebaseRepository(userMapper, carMapper)
    }
}