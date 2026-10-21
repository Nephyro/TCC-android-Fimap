package com.example.fimap.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fimap.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// classe de dados para representar o estado da tela
data class LoginUiState(
    val user: String = "", // Este campo atuará como o CPF do usuário
    val password: String = "",
    val userError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())     // Estado da tela (dados)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Funções para atualizar o estado da tela
    fun onUserChange(newUser: String) {
        _uiState.update { it.copy(user = newUser, userError = null) }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, passwordError = null) }
    }

    // Função para simular o login checando o DataStore
    fun login(onSuccess: (String) -> Unit) {
        val currentState = _uiState.value
        var hasError = false
        var uError: String? = null
        var pError: String? = null

        // Validação de Usuário (CPF)
        if (currentState.user.isEmpty()) {
            uError = "O campo usuário não pode estar vazio"
            hasError = true
        } else if (currentState.user.length < 3) {
            uError = "Usuário inválido"
            hasError = true
        }

        // Validação de Senha
        if (currentState.password.isEmpty()) {
            pError = "O campo senha não pode estar vazio"
            hasError = true
        } else if (currentState.password.length < 8) {
            pError = "Senha inválida"
            hasError = true
        }

        // Atualiza a tela com os erros básicos de digitação
        _uiState.update {
            it.copy(userError = uError, passwordError = pError)
        }


        // Se a digitação estiver correta, vamos validar no "Banco de Dados"
        if (!hasError) {
            viewModelScope.launch {
                val cpfSalvo = userPreferences.userCpf.first()
                var emailSalvo = userPreferences.userEmail.first()
                val usernameSalvo = userPreferences.userUsername.first()
                val senhaSalva = userPreferences.userPassword.first()

                // Verifica se existe alguém cadastrado
                if (cpfSalvo.isEmpty() || senhaSalva.isEmpty()) {
                    _uiState.update { it.copy(userError = "Nenhum usuário cadastrado.") }
                    return@launch
                }

                val usuarioDigitadoLimpo = currentState.user.filter { it.isDigit() }
                val cpfSalvoLimpo = cpfSalvo.filter { it.isDigit() }

                // Verificações de correspondência
                val isUsernameCorreto = (currentState.user == usernameSalvo)
                val isCpfCorreto = (usuarioDigitadoLimpo.isNotEmpty() && usuarioDigitadoLimpo == cpfSalvoLimpo)

                // Compara o e-mail digitado (ignorando maiúsculas/minúsculas e espaços)
                val isEmailCorreto = emailSalvo.isNotEmpty() && currentState.user.trim().equals(emailSalvo.trim(), ignoreCase = true)

                // Compara as credenciais
                if ((isUsernameCorreto || isCpfCorreto || isEmailCorreto) && currentState.password == senhaSalva) {

                    userPreferences.setLoggedIn(true)

                    onSuccess(usernameSalvo)

                } else {
                    _uiState.update { it.copy(passwordError = "Usuário ou senha incorretos") }
                }
            }
        }
    }
}

// Fábrica necessária para instanciar a ViewModel com o UserPreferences
class LoginViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}