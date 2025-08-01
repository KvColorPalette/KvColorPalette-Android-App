package com.kavi.droid.color.palette.app.di

import com.kavi.droid.color.palette.app.data.repository.SettingsDataRepository
import com.kavi.droid.color.palette.app.data.repository.SettingsLocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: SettingsLocalRepositoryImpl): SettingsDataRepository
}