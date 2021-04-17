package com.yuriysurzhikov.autobroker.di

import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.local.FuelTypeCache
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.mappers.*
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object EntityMapperModule {


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

    @Provides
    @Singleton
    fun provideFuelTypeLocalMapper(): IEntityMapper<FuelType, FuelTypeCache> {
        return FuelTypeLocalMapper()
    }

    @Provides
    @Singleton
    fun provideGearBoxTypeMapper(): IEntityMapper<GearboxType, GearboxTypeCache> {
        return GearBoxTypeLocalMapper()
    }

    @Provides
    @Singleton
    fun provideRegionsMapper(): IEntityMapper<Region, RegionRoom> {
        return RegionsLocalMapper()
    }
}