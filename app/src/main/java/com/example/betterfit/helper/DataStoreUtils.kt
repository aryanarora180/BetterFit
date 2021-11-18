package com.example.betterfit.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreUtils {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    private val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")

    suspend fun getAuthToken(): String? =
        dataStore.data.map { preferences -> preferences[KEY_AUTH_TOKEN] }.first()

    suspend fun storeAuthToken(newToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = newToken
        }
    }

    suspend fun signOut() {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = ""
        }
    }
}