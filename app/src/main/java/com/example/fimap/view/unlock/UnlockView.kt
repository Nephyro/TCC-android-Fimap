package com.example.fimap.view.unlock

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.fimap.R
import com.example.fimap.components.BiometricHelper
import com.example.fimap.components.PrimaryButton

@Composable
fun UnlockScreen(
    navController: NavController,
    userName: String
) {
    val context = LocalActivity.current as FragmentActivity

    // Função que chama a biometria e navega para o Dashboard se der certo
    val promptBiometrics = {
        BiometricHelper.authenticate(
            activity = context,
            title = "Desbloquear Fimap",
            subtitle = "Use sua biometria para acessar",
            onSuccess = {
                // Sucesso: vai para a Home e remove a tela de Unlock da pilha
                navController.navigate("dashboard/$userName") {
                    popUpTo("unlock") { inclusive = true }
                }
            },
            onError = { /* Tratar erro ou pedir senha manual */ }
        )
    }

    // Pede a biometria automaticamente assim que a tela abre
    LaunchedEffect(Unit) {
        promptBiometrics()
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fimap_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Fimap", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = "Usar senha do celular",
            trailingIcon = Icons.Outlined.Fingerprint,
            onClick = promptBiometrics
        )
    }
}