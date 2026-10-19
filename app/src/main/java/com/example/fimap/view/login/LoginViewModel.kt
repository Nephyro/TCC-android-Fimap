package com.example.fimap.view.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// classe de dados para representar o estado da tela
data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val userError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUserChange(newUser: String) {
        _uiState.update { it.copy(user = newUser, userError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, passwordError = null) }
    }

    fun login(onSuccess: () -> Unit) {
        val currentState = _uiState.value
        var hasError = false
        var uError: String? = null
        var pError: String? = null

        // Validação de Usuário
        if (currentState.user.isEmpty()) {
            uError = "O campo usuário não pode estra vazio"
            hasError = true
        } else if (currentState.user.length < 3) {
            uError = "Usuário inválido"
            hasError = true
        }

        // Validação de Senha
        if (currentState.password.isEmpty()) {
            uError = "O campo senha não pode estra vazio"
            hasError = true
        } else if (currentState.password.length < 8) {
            uError = "Senha inválida"
            hasError = true
        }

        // Atualiza a tela com os erros
        _uiState.update {
            it.copy(userError = uError, passwordError = pError)
        }

        // Se não houver erro, prossegue com o login
        if (!hasError) {
            // Local para a chamada na API
            onSuccess()
        }

    }
}