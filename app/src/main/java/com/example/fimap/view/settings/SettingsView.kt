package com.example.fimap.view.settings

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fimap.R
import com.example.fimap.components.BiometricHelper
import com.example.fimap.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel()
) {
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()
    val context = LocalActivity.current as FragmentActivity
    val scrollState = rememberScrollState()

    // 1. Cria o comportamento para a TopAppBar recolher ao rolar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        // 2. Conecta o nestedScroll no Scaffold para ele detetar o movimento de arrastar
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(0.dp, 30.dp, 0.dp, 0.dp),
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.fimap_logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(28.dp).clip(RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Fimap", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Notificações */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificações"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho da página
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Configurações do Sistema", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Preferências e Parâmetros", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Título da Seção
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.Shield, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Segurança e Acesso", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Proteção Biométrica", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card de Configurações
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Item 1: Alterar Senha
                    SettingsItem(
                        icon = Icons.Outlined.LockReset,
                        title = "Alterar Senha",
                        subtitle = "Atualizada há 42 dias",
                        trailing = {
                            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
                        },
                        onClick = { /* Navegar para alterar senha */ }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))

                    // Item 2: Biometria e Face ID
                    SettingsItem(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometria e Face ID",
                        subtitle = "Desbloqueio rápido e seguro",
                        trailing = {
                            Switch(
                                checked = isBiometricEnabled,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        if (BiometricHelper.isBiometricAvailable(context)) {
                                            BiometricHelper.authenticate(
                                                activity = context,
                                                title = "Ativar Biometria",
                                                subtitle = "Confirme sua identidade para ativar",
                                                onSuccess = { viewModel.toggleBiometric(true) },
                                                onError = { /* Tratar erro */ }
                                            )
                                        }
                                    } else {
                                        viewModel.toggleBiometric(false)
                                    }
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color.Black)
                            )
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))

                    // Item 3: Autenticação 2FA
                    SettingsItem(
                        icon = Icons.Outlined.GppGood,
                        title = "Autenticação 2FA",
                        subtitle = "App autenticador integrado",
                        trailing = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFF0F0F0),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text(text = "Ativado", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                                }
                                Icon(imageVector = Icons.Outlined.Tune, contentDescription = null, tint = Color.Gray)
                            }
                        }
                    )


                }
            }

            Spacer(modifier = Modifier.padding(top = 200.dp))

            Button(
                onClick = {
                    viewModel.logout(
                        onSuccess = {
                            navController.navigate("login") {
                                // Limpa todo o histórico de telas
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Red
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "ícone de sair",
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.padding(start = 10.dp))

                    Text(
                        text = "Sair da Conta",
                        fontSize = 18.sp
                    )
                }
            }

            Text(
                text = "Ambiente seguro com criptografia de ponta a ponta",
                fontSize = 12.sp,
                color = Color.Gray
                )



        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailing: @Composable () -> Unit,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF5F5F5),
            modifier = Modifier.size(40.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.padding(8.dp), tint = Color.Black)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
        }
        trailing()
    }
}