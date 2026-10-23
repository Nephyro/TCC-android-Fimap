package com.example.fimap.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// 1. Definição das abas da barra
sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    // Usamos um identificador base ou genérico para sabermos que é a home
    object Inicio : BottomNavItem("dashboard", "Início", Icons.Default.Home)
    object ChatIa : BottomNavItem("chat_ia", "Chat IA", Icons.Default.Person)
    object Simular : BottomNavItem("simular", "Simular", Icons.Default.Person)
    object Metas : BottomNavItem("metas", "Metas", Icons.Default.Person)
    object Social : BottomNavItem("social", "Social", Icons.Default.Person)
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Inicio,
        BottomNavItem.ChatIa,
        BottomNavItem.Simular,
        BottomNavItem.Metas,
        BottomNavItem.Social
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Descobrimos o userName atual olhando para os argumentos da tela onde estamos (se estivermos no dashboard)
    val currentArguments = navBackStackEntry?.arguments
    val currentUserName = currentArguments?.getString("userName")

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            // O item "Início" está selecionado se a rota atual começar com "dashboard/"
            val selected = if (item.route == "dashboard") {
                currentRoute?.startsWith("dashboard/") == true
            } else {
                currentRoute == item.route
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        if (item.route == "dashboard") {
                            if (currentUserName != null) {
                                // Se temos o nome, navegamos de volta garantindo o destino exato
                                navController.navigate("dashboard/$currentUserName") {
                                    popUpTo("dashboard/$currentUserName") { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else {
                                // Se não tivermos o nome em mãos, o popBackStack simples
                                // já desempilha a tela de config e revela o dashboard anterior!
                                navController.popBackStack()
                            }
                        } else {
                            // Comportamento normal para as outras abas
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selected) Color.White else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}