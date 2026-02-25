package com.djoudinis.barcalculator.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserDataRepository(private val context: Context) {

    private val gson = Gson()

    companion object {
        private val BAR_VISITS_KEY = stringPreferencesKey("bar_visits")
        private val WEIGHT_KEY = stringPreferencesKey("user_weight")
        private val IS_MALE_KEY = stringPreferencesKey("user_is_male")
        // Add other keys for settings you want to persist
    }

    // --- BarVisits ---
    val barVisitsFlow: Flow<List<BarVisit>> = context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[BAR_VISITS_KEY] ?: "[]"
            val type = object : TypeToken<List<BarVisit>>() {}.type
            gson.fromJson(jsonString, type) ?: emptyList()
        }

    suspend fun saveBarVisits(barVisits: List<BarVisit>) {
        val jsonString = gson.toJson(barVisits)
        context.dataStore.edit { preferences ->
            preferences[BAR_VISITS_KEY] = jsonString
        }
    }

    // --- User Settings (Weight, Gender, etc.) ---
    val weightFlow: Flow<Float> = context.dataStore.data.map { it[WEIGHT_KEY]?.toFloatOrNull() ?: 80f }
    val isMaleFlow: Flow<Boolean> = context.dataStore.data.map { it[IS_MALE_KEY]?.toBooleanStrictOrNull() ?: true }

    suspend fun saveWeight(weight: Float) {
        context.dataStore.edit { it[WEIGHT_KEY] = weight.toString() }
    }

    suspend fun saveIsMale(isMale: Boolean) {
        context.dataStore.edit { it[IS_MALE_KEY] = isMale.toString() }
    }
}
