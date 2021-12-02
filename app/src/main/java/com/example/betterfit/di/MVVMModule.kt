package com.example.betterfit.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.betterfit.data.AppRepository
import com.example.betterfit.helper.DataStoreUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MVVMModule {

    @Provides
    @Singleton
    fun provideDataStoreUtils(@ApplicationContext context: Context): DataStoreUtils {
        return DataStoreUtils(
            PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("preferences")
            }
        )
    }

    @Provides
    @Singleton
    fun provideRepository(): AppRepository {
        return AppRepository()
    }
}