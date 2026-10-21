package com.example.fimap.view.cadastro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fimap.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// classe que vai armazenar o estado da tela de cadastro
data class CadastroUiState(
    val name: String = "",
    val cpf: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val nameError: String? = null,
    val cpfError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

// classe que vai gerenciar o estado da tela de cadastro
class CadastroViewModel(
    private val userPreferences: UserPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(CadastroUiState())
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    // função que vai atualizar o estado da tela de cadastro
    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(name = newValue, nameError = null) }
    }

    fun onCpfChange(newValue: String) {
        val numbersOnly = newValue.filter { it.isDigit() }
        if (numbersOnly.length <= 11) {
            _uiState.update { it.copy(cpf = numbersOnly, cpfError = null) } // Melhor salvar só os números puros
        }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue, usernameError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    // função que vai simular o cadastro e salvar os dados
    fun cadastrar(onSucess: () -> Unit) {
        val state = _uiState.value
        var hasError = false

        var nameError: String? = null
        var cpfError: String? = null
        var usernameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null

        if (state.name.isEmpty()) { nameError = "O campo nome não pode estar vazio"; hasError = true }

        if (state.cpf.isEmpty()) { cpfError = "O campo cpf não pode estar vazio"; hasError = true }
        else if (state.cpf.length < 11) { cpfError = "CPF inválido"; hasError = true }

        if (state.email.isEmpty()) { emailError = "O campo email não pode estar vazio"; hasError = true }

        if (state.username.isEmpty()) { usernameError = "O campo username não pode estar vazio"; hasError = true }

        if (state.password.isEmpty()) { passwordError = "O campo senha não pode estar vazio"; hasError = true }
        else if (state.password.length < 8) { passwordError = "Senha deve conter no mínimo 8 caracteres"; hasError = true }

        // Atualiza a tela com os erros
        _uiState.update {
            it.copy(
                nameError = nameError,
                cpfError = cpfError,
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError
            )
        }

        // Se passar nas validações, salva os dados no DataStore
        if (!hasError) {
            viewModelScope.launch {
                // Aqui estamos chamando a função que criamos no UserPreferences
                userPreferences.saveUser(
                    name = state.name,
                    cpf = state.cpf,
                    email = state.email,
                    username = state.username,
                    pass = state.password
                )
                // Após salvar, dispara a navegação
                userPreferences.setLoggedIn(true)
                onSucess()
            }
        }
    }
}

// Fábrica necessária para instanciar a ViewModel com o UserPreferences
class CadastroViewModelFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CadastroViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("ViewModel desconhecida")
    }
}