package com.yuriysurzhikov.autobroker.di

import com.yuriysurzhikov.autobroker.repository.utils.LocationConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ConvertersModule {

    @Provides
    fun provideLocationConverter(): LocationConverter {
        return LocationConverter()
    }
}