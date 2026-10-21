package com.example.fimap

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import com.example.fimap.data.UserPreferences
import com.example.fimap.ui.theme.FimapTheme
import com.example.fimap.view.navigation.AppNavigation
import kotlinx.coroutines.flow.first

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        val userPreferences = UserPreferences(this)

        setContent {
            FimapTheme {
                // Variável de estado para segurar a rota inicial
                var startDestination by remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    // Lê as três informações necessárias
                    val isBiometricEnabled = userPreferences.isBiometricEnabled.first()
                    val isLoggedIn = userPreferences.isLoggedIn.first()
                    val savedUsername = userPreferences.userUsername.first()

                    startDestination = when {
                        // 1º REGRA: Se NÃO está logado (fez logout ou é a 1ª vez), SEMPRE vai pro login.
                        !isLoggedIn -> "login"

                        // 2º REGRA: Se ESTÁ logado e a biometria tá ativada, vai pra tela de bloqueio.
                        isBiometricEnabled -> "unlock"

                        // 3º REGRA: Se ESTÁ logado, tem username salvo e não tem biometria, vai pro dashboard direto.
                        savedUsername.isNotEmpty() -> "dashboard/$savedUsername"

                        // Fallback de segurança
                        else -> "login"
                    }
                }

                if (startDestination == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    // Passa a ROTA calculada e não o booleano
                    AppNavigation(startDestination = startDestination!!)
                }
            }
        }
    }
}