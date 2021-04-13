package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideIUserRepository(localDatabase: LocalDatabase): IUserRepository {
        return UserLocalRepositoryImpl(localDatabase)
    }
}