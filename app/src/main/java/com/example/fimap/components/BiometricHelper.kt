package com.example.fimap.components

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

// Objeto para auxiliar na autenticação biométrica
object BiometricHelper {
    fun authenticate (
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)      // Contexto da Activity
        val biometricPrompt = BiometricPrompt(activity, executor,            // Prompt de autenticação biométrica
            object : BiometricPrompt.AuthenticationCallback() {    // Callback para lidar com os resultado
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {      // Se a autenticação for bem sucedida
                    super.onAuthenticationSucceeded(result)     // chama a função onSuccess
                    onSuccess()     // onSuccess é uma função que será definida na tela de login
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {    // Se a autenticação falhar
                    super.onAuthenticationError(errorCode, errString)                           // Chama a função onError
                    onError(errString.toString())
                }
            })

        // Configurações do prompt de autenticação
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)        // Título do prompt
            .setSubtitle(subtitle)      // Subtítulo do prompt
            // Autenticação por biometria ou senha
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()        // Constrói o prompt

        biometricPrompt.authenticate(promptInfo)
    }

    // Verifica se o dispositivo tem suporte a autenticação biométrica
    fun isBiometricAvailable(activity: FragmentActivity): Boolean {
        val biometricManager = BiometricManager.from(activity)      // Instância do BiometricManager
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS     // Retorna true se o dispositivo tem suporte a autenticação biométrica
    }
}