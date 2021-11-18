package com.example.betterfit.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
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
    fun provideRepository(@ApplicationContext context: Context): AppRepository {
        return AppRepository(context)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("preferences")
    }

    @Provides
    @Singleton
    fun provideDataStoreUtils(): DataStoreUtils {
        return DataStoreUtils()
    }
}