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
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 30.dp, 0.dp, 0.dp),
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
        ) {
            // Cabeçalho abaixo da TopBar

            Text(
                text = "Olá, ${state.userName}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Seu panorama consolidado",
                fontSize = 14.sp,
                color = Color.Gray
            )



        }
    }


}