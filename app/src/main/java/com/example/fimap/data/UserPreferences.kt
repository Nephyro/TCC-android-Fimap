package com.example.fimap.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences (private val context: Context) {

    companion object {
        val BIOMETRIC_KEY = booleanPreferencesKey("is_biometric_enabled")

        // chaves para simular o cadastro
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_CPF_KEY = stringPreferencesKey("user_cpf")

        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_USERNAME_KEY = stringPreferencesKey("user_username")
        val USER_PASSWORD_KEY = stringPreferencesKey("user_password")
    }

    // Biometria
    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data.map { it[BIOMETRIC_KEY] ?: false }
    suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BIOMETRIC_KEY] = enabled }
    }

    // --- LEITURA DE DADOS DO USUÁRIO ---
    val userName: Flow<String> = context.dataStore.data.map { it[USER_NAME_KEY] ?: "" }
    val userCpf: Flow<String> = context.dataStore.data.map { it[USER_CPF_KEY] ?: "" }
    val userEmail: Flow<String> = context.dataStore.data.map { it[USER_EMAIL_KEY] ?: "" }
    val userUsername: Flow<String> = context.dataStore.data.map { it[USER_USERNAME_KEY] ?: "" }
    val userPassword: Flow<String> = context.dataStore.data.map { it[USER_PASSWORD_KEY] ?: "" }

    val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[IS_LOGGED_IN_KEY] ?: false }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = loggedIn
        }
    }

    // --- GRAVAÇÃO (Simulando o Cadastro) ---
    suspend fun saveUser(name: String, cpf: String, email: String, username: String, pass: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_CPF_KEY] = cpf
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_USERNAME_KEY] = username
            preferences[USER_PASSWORD_KEY] = pass
        }
    }
}
