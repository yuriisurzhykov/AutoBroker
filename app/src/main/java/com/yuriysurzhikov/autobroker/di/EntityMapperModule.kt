package com.yuriysurzhikov.autobroker.di

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.yuriysurzhikov.autobroker.model.entity.*
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.local.UserLocalDao
import com.yuriysurzhikov.autobroker.repository.mappers.*
import com.yuriysurzhikov.autobroker.repository.sync.FirebaseSyncRepository
import com.yuriysurzhikov.autobroker.repository.utils.ImagesListConverter
import com.yuriysurzhikov.autobroker.repository.utils.LocationConverter
import com.yuriysurzhikov.autobroker.repository.utils.RegionNumberConverter
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
    fun provideLocalUserMapper(
        userLocalDao: UserLocalDao,
        locationMapper: IEntityMapper<UserLocation?, UserLocationRoom?>
    ): IEntityMapper<User?, UserRoom?> {
        return UserLocalMapper(userLocalDao, locationMapper)
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

    @Provides
    @Singleton
    fun provideLocationMapper(): IEntityMapper<UserLocation?, UserLocationRoom?> {
        return LocationLocalMapper()
    }

    @Provides
    @Singleton
    fun provideRemoteUserMapper(converter: LocationConverter): IEntityMapper<User, Map<String, Any?>> {
        return UserRemoteMapper(converter)
    }

    @Provides
    @Singleton
    fun provideCarBrandLocalMapper(modelMapper: IEntityMapper<CarModel, CarModelRoom>): IEntityMapper<CarBrand, CarWithModelsRoom> {
        return CarBrandLocalMapper(modelMapper)
    }

    @Provides
    @Singleton
    fun provideCarModelLocalMapper(): IEntityMapper<CarModel, CarModelRoom> {
        return CarModelLocalMapper()
    }

    @Provides
    @Singleton
    fun provideCarLocalMapper(): IEntityMapper<Car, CarRoom> {
        return CarMapper()
    }

    @Provides
    @Singleton
    fun provideCarFirebaseMapper(
        regionNumberConverter: RegionNumberConverter,
        imagesListConverter: ImagesListConverter
    ): IEntityMapper<Car, Map<String, Any>> {
        return CarRemoteMapper(regionNumberConverter, imagesListConverter)
    }
}