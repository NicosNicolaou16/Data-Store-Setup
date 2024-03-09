package com.nicos.datastoresetup.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

object PreferencesDataStoreHelper {

    private const val PREFERENCES_DATA_STORE_NAME = "preferences_data_store_name"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_DATA_STORE_NAME)

    internal suspend fun saveStringValue(
        value: String,
        key: Preferences.Key<String>,
        context: Context
    ) {
        context.dataStore.edit { saveData ->
            saveData[key] = value
        }
    }

    internal fun getStringValueFlow(key: Preferences.Key<String>, context: Context): Flow<String?> =
        context.dataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emit(emptyPreferences())
                    else -> throw exception
                }
            }.map { readData ->
                readData[key]
            }

    internal suspend fun removeStringValueWithSpecificKey(
        key: Preferences.Key<String>,
        context: Context
    ) {
        context.dataStore.edit { it.remove(key) }
    }

    internal suspend fun removeAllValues(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}