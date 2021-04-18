package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.repository.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
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
        locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>
    ): IUserLocalRepository {
        return UserLocalRepositoryImpl(
            firebaseRepository,
            localDatabase,
            syncDatabase,
            userMapper,
            locationMapper
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseUserRepository(userMapper: IEntityMapper<User, Map<String, Any?>>): UserFirebaseRepository {
        return UserFirebaseRepository(userMapper)
    }
}