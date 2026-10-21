package com.example.fimap.view.cadastro

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
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.fimap.components.CpfVisualTransformation

@Composable
fun CadastroScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CadastroViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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
            modifier = Modifier
                .size(120.dp),
            painter = painterResource(R.drawable.fimap_logo),
            contentDescription = "logo app"
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Crie sua conta",
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Cadastre-se para acesssar o Fimap",
            color = Color.Black
        )

        Spacer(modifier = Modifier.padding(5.dp))

        FormCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                CustomTextField(
                    value = state.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = "Nome Completo",
                    placeholder = "Seu nome completo",
                    leadingIcon = Icons.Default.Person,
                    errorMessage = state.nameError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )

                CustomTextField(
                    value = state.cpf,
                    onValueChange = { viewModel.onCpfChange(it) },
                    label = "CPF",
                    placeholder = "000.000.000-00",
                    leadingIcon = Icons.Default.Badge,
                    errorMessage = state.cpfError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                    visualTransformation = CpfVisualTransformation()
                )

                CustomTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = "E-mail",
                    placeholder = "seu@email.com",
                    leadingIcon = Icons.Default.Email,
                    errorMessage = state.emailError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )

                CustomTextField(
                    value = state.username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    label = "Nome de usuário",
                    placeholder = "Seu nome de usuário",
                    leadingIcon = Icons.Default.Person,
                    errorMessage = state.usernameError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )

                PasswordTextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    placeholder = "Mínimo 8 caracteres",
                    errorMessage = state.passwordError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )

                Spacer(modifier = Modifier.height(5.dp))

                PrimaryButton(
                    text = "Cadastrar",
                    trailingIcon = Icons.Default.ArrowForward,
                    onClick = {
                        viewModel.cadastrar(
                            onSucess = {
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                Row {
                    Text(
                        text = "Já tem uma conta? ",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Fazer Login",
                        color = Color.Black,
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }


            }
        }


    }
}