package com.example.tbank.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.tbank.domain.repository.TokensRepository
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokensRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokensRepository {


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    override suspend fun getAccessToken() = context.dataStore.data.map {
        preferences -> preferences[ACCESS_TOKEN_KEY]
    }.firstOrNull()

    override suspend fun getRefreshToken() = context.dataStore.data.map {
        preferences -> preferences[REFRESH_TOKEN_KEY]
    }.firstOrNull()

    override suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun isLogged(): Boolean {
        return getAccessToken() != null
    }

    override suspend fun clearTokens(){
        context.dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    override suspend fun saveId(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = id.toString()
        }
    }

    override suspend fun getId() = context.dataStore.data.map {
        preferences -> preferences[ID_KEY]?.toInt()
    }.firstOrNull()

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val ID_KEY = stringPreferencesKey("id_key")
        private const val PREFS_NAME = "token_prefs"
    }
}
