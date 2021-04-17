package com.yuriysurzhikov.autobroker.di

import android.content.Context
import androidx.room.Room
import com.yuriysurzhikov.autobroker.repository.local.*
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, Const.USER_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserLocalDao(localDatabase: LocalDatabase): UserLocalDao {
        return localDatabase.userRepository()
    }

    @Provides
    @Singleton
    fun provideSyncDatabase(@ApplicationContext context: Context): SyncDatabase {
        return Room.databaseBuilder(context, SyncDatabase::class.java, Const.VOCAB_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSyncDao(database: SyncDatabase) : RegionsDao {
        return database.getRegionsDao()
    }

    @Provides
    @Singleton
    fun provideFuelTypesDao(database: SyncDatabase): FuelTypesDao {
        return database.getFuelTypesDao()
    }

    @Provides
    @Singleton
    fun provideGearBoxTypesDao(database: SyncDatabase): GearBoxTypeDao {
        return database.getGearBoxTypesDao()
    }

    object Const {
        const val USER_DB_NAME = "user_database.db"
        const val VOCAB_DB_NAME = "vocabulary_database.db"
    }
}