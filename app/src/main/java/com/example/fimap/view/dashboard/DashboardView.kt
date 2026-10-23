package com.example.fimap.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fimap.components.FimapTopAppBar
import com.example.fimap.components.PrimaryButton

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DashboardViewModel = viewModel()
) {
    var scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            FimapTopAppBar(
                onMenuClick = {  },
                onNotificationsClick = {
                    navController.navigate("notifications")
                },
                onSettingsClick = {
                    navController.navigate("config")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(scrollState)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Cabeçalho abaixo da TopBar (Olá, Arthur)
            Text(
                text = "Olá, ${state.userName}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Seu panorama consolidado",
                fontSize = 16.sp,
                color = Color.Gray
            )

            PrimaryButton(
                text = "Sair",
                trailingIcon = Icons.Default.ArrowForward,
                onClick = {
                    viewModel.logout(
                        onSuccess = {
                            navController.navigate("login") {
                                // Limpa absolutamente todo o histórico de telas
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            )




        }
    }


}