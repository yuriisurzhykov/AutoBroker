package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.mappers.UserLocalMapper
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
        localDatabase: LocalDatabase,
        syncDatabase: SyncDatabase,
        userMapper: UserLocalMapper,
        locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>
    ): IUserRepository {
        return UserLocalRepositoryImpl(localDatabase, syncDatabase, userMapper, locationMapper)
    }
}