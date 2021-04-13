package com.yuriysurzhikov.autobroker.di

import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserLocalRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.mappers.FirebaseUserMapper
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
        userMapper: UserLocalMapper
    ): IUserRepository {
        return UserLocalRepositoryImpl(localDatabase, userMapper)
    }

    @Provides
    @Singleton
    fun provideLocalUserMapper(): IEntityMapper<User?, UserRoom?> {
        return UserLocalMapper()
    }

    @Provides
    @Singleton
    fun provideFirebaseMapper(): IEntityMapper<FirebaseUser, User> {
        return FirebaseUserMapper()
    }
}