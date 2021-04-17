package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.mappers.UserLocalMapper
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
        userMapper: UserLocalMapper
    ): IUserRepository {
        return UserLocalRepositoryImpl(localDatabase, userMapper)
    }
}