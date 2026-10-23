package com.example.fimap.view.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fimap.data.UserPreferences
import com.example.fimap.view.cadastro.CadastroScreen
import com.example.fimap.view.cadastro.CadastroViewModel
import com.example.fimap.view.cadastro.CadastroViewModelFactory
import com.example.fimap.view.dashboard.DashboardScreen
import com.example.fimap.view.dashboard.DashboardViewModel
import com.example.fimap.view.login.LoginScreen
import com.example.fimap.view.login.LoginViewModel
import com.example.fimap.view.login.LoginViewModelFactory
import com.example.fimap.view.settings.SettingsScreen
import com.example.fimap.view.settings.SettingsViewModel
import com.example.fimap.view.settings.SettingsViewModelFactory
import com.example.fimap.view.unlock.UnlockScreen


@Composable
fun AppNavigation(
    isBiometricEnabled: Boolean = false,
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // TELA DE LOGIN
        composable(route = "login") {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val viewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(userPreferences)
            )
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        // TELA DE DESBLOQUEIO (BIOMETRIA)
        composable(route = "unlock") {
            val context = LocalContext.current
            val userPreferences = remember { UserPreferences(context) }

            // 1. Usamos null como estado inicial para saber que ainda está carregando
            val savedUserName by userPreferences.userUsername.collectAsState(initial = null as String?)

            // 2. Só chama a tela (e o LaunchedEffect da biometria) quando o nome já foi carregado
            if (savedUserName != null) {
                UnlockScreen(navController = navController, userName = savedUserName!!)
            } else {
                // Tela em branco rápida enquanto lê o disco
                Box(modifier = Modifier.fillMaxSize())
            }
        }

        // TELA DE DASHBOARD
        composable(route = "dashboard/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)

            // Para injetar o UserPreferences e não perder o argumento userName da rota:
            val viewModel: DashboardViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return DashboardViewModel(
                            savedStateHandle = SavedStateHandle(
                                mapOf("userName" to backStackEntry.arguments?.getString("userName"))
                            ),
                            userPreferences = userPreferences
                        ) as T
                    }
                }
            )

            DashboardScreen(navController = navController, viewModel = viewModel)
        }

        // TELA DE CONFIGURAÇÕES
        composable(route = "config") {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val viewModel: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(userPreferences)
            )
            SettingsScreen(navController = navController, viewModel = viewModel)
        }

        // TELA DE CADASTRO
        composable(route = "cadastro") {
            val context = LocalContext.current
            val userPreferences = UserPreferences(context)
            val viewModel: CadastroViewModel = viewModel(
                factory = CadastroViewModelFactory(userPreferences)
            )
            CadastroScreen(navController = navController, viewModel = viewModel)
        }
    }
}