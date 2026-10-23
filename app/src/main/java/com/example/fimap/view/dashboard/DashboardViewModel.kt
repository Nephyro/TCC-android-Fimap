package com.example.fimap.view.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fimap.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// O estado inicial muda para pegar a variável recebida
data class DashboardUiState(
    val userName: String = "",
    val isLoading: Boolean = false
)

class DashboardViewModel(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences // INJETADO AQUI
) : ViewModel() {

    private val nomeRecebido: String = savedStateHandle.get<String>("userName") ?: "Usuário"

    private val _uiState = MutableStateFlow(DashboardUiState(userName = nomeRecebido))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    // Nova função de Logout
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Desliga a sessão no DataStore
            userPreferences.setLoggedIn(false)
            onSuccess()
        }
    }
}

// Fábrica necessária para o DashboardViewModel agora
class DashboardViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Nota: O SavedStateHandle é injetado automaticamente pela navegação se usarmos a abordagem padrão, mas com Factory customizada precisamos criar assim no Navigation
            return DashboardViewModel(
                savedStateHandle = SavedStateHandle(), // O Navigation vai sobrescrever isso na rota
                userPreferences = userPreferences
            ) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}