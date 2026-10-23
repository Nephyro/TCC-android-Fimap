package com.example.fimap.view.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fimap.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SettingsViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _isBiometricEnabled = MutableStateFlow(false)
    val isBiometricEnabled: StateFlow<Boolean> = _isBiometricEnabled.asStateFlow()

    init {
        // Ao iniciar a tela, lê o status salvo no DataStore
        viewModelScope.launch {
            userPreferences.isBiometricEnabled.collect { savedState ->
                _isBiometricEnabled.value = savedState
            }
        }
    }

    fun toggleBiometric(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setBiometricEnabled(enabled) // Salva fisicamente
            _isBiometricEnabled.value = enabled          // Atualiza a tela
        }
    }

    // Nova função de Logout
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Desliga a sessão no DataStore
            userPreferences.setLoggedIn(false)
            onSuccess()
        }
    }
}


// Fábrica necessária porque a ViewModel agora tem um construtor com parâmetros
class SettingsViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}