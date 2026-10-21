package com.example.fimap.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fimap.R
import com.example.fimap.components.CustomTextField
import com.example.fimap.components.FormCard
import com.example.fimap.components.PasswordTextField
import com.example.fimap.components.PrimaryButton

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = viewModel()     // Instância do ViewModel
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    // Observa o estado que vem do ViewModel
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(R.drawable.fimap_logo),
            contentDescription = "logo app"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Bem-vindo(a)",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Acesse sua conta para continuar",
            color = Color.Black
        )

        Spacer(modifier = Modifier.padding(20.dp))

        FormCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Componente customizado (Usuário)
                CustomTextField(
                    value = state.user,
                    onValueChange = { viewModel.onUserChange(it) },
                    label = "Nome de usuário ou E-mail",
                    placeholder = "Seu usuário",
                    leadingIcon = Icons.Default.Person,
                    errorMessage = state.userError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )

                // Componente customizado (Senha)
                PasswordTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "Sua senha",
                    errorMessage = state.passwordError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                Text(
                    text = "Esqueci minha senha",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("cadastro")
                        }
                )

                // Componente customizado (Botão)
                PrimaryButton(
                    text = "Entrar",
                    trailingIcon = Icons.Default.ArrowForward,
                    onClick = {
                        viewModel.login(
                            onSuccess = { nomeDigitado ->
                                navController.navigate("dashboard/$nomeDigitado") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row() {
                    Text(
                        text = "Não tem uma conta? ",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Cadastre-se",
                        color = Color.Black,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("cadastro")
                            }
                    )
                }

            }
        }




    }
}