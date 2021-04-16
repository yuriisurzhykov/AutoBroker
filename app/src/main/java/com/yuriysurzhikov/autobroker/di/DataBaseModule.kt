package com.yuriysurzhikov.autobroker.di

import android.content.Context
import androidx.room.Room
import com.yuriysurzhikov.autobroker.repository.sync.ISyncRepository
import com.yuriysurzhikov.autobroker.repository.local.*
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.repository.sync.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.sync.SyncEntitiesDao
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
    fun provideSyncDao(database: SyncDatabase) : SyncEntitiesDao {
        return database.getSyncDao()
    }

    @Provides
    @Singleton
    fun provideSyncRepository(syncEntitiesDao: SyncEntitiesDao): ISyncRepository {
        return LocalSyncRepository(
            syncEntitiesDao
        )
    }

    object Const {
        const val USER_DB_NAME = "user_database.db"
        const val VOCAB_DB_NAME = "vocabulary_database.db"
    }
}