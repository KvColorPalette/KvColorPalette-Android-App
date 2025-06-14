package com.kavi.droid.color.palette.app.data

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object AppDatastore {

    const val APP_THEME_TYPE= "APP_THEME_TYPE"
    const val APP_THEME_BASE_COLOR = "APP_THEME_BASE_COLOR"
    const val APP_THEME_FIRST_COLOR = "APP_THEME_FIRST_COLOR"
    const val APP_THEME_SECOND_COLOR = "APP_THEME_SECOND_COLOR"

    lateinit var appContext: Context

    val Context.appDatastore by preferencesDataStore(
        name = "SURVEY_APP_DATA",
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(context, "KV_COLOR_PALETTE_APP_DATA")
            )
        }
    )

    fun init(context: Context) {
        appContext = context
    }

    suspend fun storeValue(key: String, value: Any) {
        appContext.appDatastore.edit { preference ->
            when(value::class) {
                Boolean::class -> {
                    preference[booleanPreferencesKey(key)] = value as Boolean
                }
                Int::class -> {
                    preference[intPreferencesKey(key)] = value as Int
                }
                Long::class -> {
                    preference[longPreferencesKey(key)] = value as Long
                }
                Float::class -> {
                    preference[floatPreferencesKey(key)] = value as Float
                }
                String::class -> {
                    preference[stringPreferencesKey(key)] = value as String
                }
            }
        }
    }

    suspend inline fun <reified T>storeObject(key: String, value: T) {
        val jsonString = Json.encodeToString(value)
        appContext.appDatastore.edit { preference ->
            preference[stringPreferencesKey(key)] = jsonString
        }
    }

    suspend inline fun <reified T> clearValue(key: String) {
        appContext.appDatastore.edit { preference ->
            when(T::class) {
                Boolean::class -> {
                    preference.remove(booleanPreferencesKey(key))
                }
                Int::class -> {
                    preference.remove(intPreferencesKey(key))
                }
                Long::class -> {
                    preference.remove(longPreferencesKey(key))
                }
                Float::class -> {
                    preference.remove(floatPreferencesKey(key))
                }
                else -> {
                    preference.remove(stringPreferencesKey(key))
                }
            }
        }
    }

    fun retrieveBoolean(key: String): Flow<Boolean> =
        appContext.appDatastore.data.map { preference ->
            preference[booleanPreferencesKey(key)] ?: run {
                false
            }
        }

    fun retrieveInt(key: String): Flow<Int> =
        appContext.appDatastore.data.map { preference ->
            preference[intPreferencesKey(key)] ?: run {
                0
            }
        }

    fun retrieveLong(key: String): Flow<Long> =
        appContext.appDatastore.data.map { preference ->
            preference[longPreferencesKey(key)] ?: run {
                0
            }
        }

    fun retrieveFloat(key: String): Flow<Float> =
        appContext.appDatastore.data.map { preference ->
            preference[floatPreferencesKey(key)] ?: run {
                0F
            }
        }

    fun retrieveString(key: String): Flow<String> =
        appContext.appDatastore.data.map { preference ->
            preference[stringPreferencesKey(key)] ?: run {
                "NULL"
            }
        }

    inline fun <reified T>retrieveObject(key: String): Flow<T?> =
        appContext.appDatastore.data.map { preference ->
            val jsonString = preference[stringPreferencesKey(key)] ?: run { "" }
            if (jsonString.isNotBlank())
                Json.decodeFromString(jsonString)
            else
                null
        }
}