package com.example.fimap.view.login

import android.text.util.Linkify
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fimap.R
import com.example.fimap.R.drawable.fimap_logo

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var user by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    var isFocusedPerson by remember { mutableStateOf(false) }   // Variável para rastrear se o campo de usuário está focado

    var isFocusedSenha by remember { mutableStateOf(false) }    // Variável para rastrear se o campo de senha está focado

    var isSubmitted by remember { mutableStateOf(false) }   // Variável para rastrear se o formulário foi submetido

    // Variáveis de erro para os campos (Usuário e Senha)
    var isUserInvalid by remember { mutableStateOf(false) }
    var isPasswordInvalid by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp),
            painter = painterResource(
                fimap_logo
            ),
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

        Card(
            modifier = Modifier
                .width(350.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = user,
                    onValueChange = {
                        user = it
                        if (isSubmitted) isSubmitted = false
                                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedPerson = focusState.isFocused },
                    label =  {
                        Text(text = "Nome de usuário ou E-mail")
                    },
                    placeholder = {
                        Text(text = "Seu usuário")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ícone pessoa",
                            tint = when{
                                isSubmitted && user.isEmpty() || isUserInvalid -> Color.Red
                                isFocusedPerson -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },
                    isError = isSubmitted && (user.isEmpty() || isUserInvalid),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down) // Move o foco para o próximo campo
                        }
                    ),

                    supportingText = {
                        if (isSubmitted && user.isEmpty()) {
                            Text(
                                text = "O campo usuário não pode estar vazio",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                    } else if (isUserInvalid) {
                            Text(
                                text = "Usuário inválido",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Blue,
                        focusedBorderColor = Color.Blue,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        errorTextColor = Color.Black
                    )
                )



                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                                    if (isSubmitted) isSubmitted = false
                                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedSenha = focusState.isFocused },
                    label =  {
                        Text(text = "Senha")
                    },
                    placeholder = {
                        Text(text = "Sua senha")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Ícone pessoa",
                            tint = when {
                                isSubmitted && (password.isEmpty() || isPasswordInvalid) -> Color.Red
                                isFocusedSenha -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },

                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Ocultar senha" else "Mostrar senha"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = image,
                                contentDescription = description,
                                tint = when {
                                    isSubmitted && (password.isEmpty() || isPasswordInvalid) -> Color.Red
                                    isFocusedSenha -> Color.Blue
                                    else -> Color.Gray
                                }
                            )
                        }
                    },

                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),

                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),

                    isError = isSubmitted && (password.isEmpty() || isPasswordInvalid),

                    supportingText = {
                        if (isSubmitted && password.isEmpty()) {
                            Text(
                                text = "O campo senha não pode estar vazio",
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        } else if (isPasswordInvalid) {
                            Text(
                                text = "Senha inválida",
                                color = Color.Red,
                                fontSize = 12.sp
                                )
                        }
                    },


                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Blue,
                        focusedBorderColor = Color.Blue,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        errorTextColor = Color.Black
                    )
                )

                Text(
                    text = "Esqueci minha senha",
                    fontSize = 12.sp,
                    color = Color.Black,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("")
                        }
                )


                Button(
                    onClick = {
                        isSubmitted = true

                        if (password.isNotEmpty() && password.length < 6) {
                            isPasswordInvalid = true
                        } else {
                            isPasswordInvalid = false
                        }

                        if (user.isNotEmpty() && password.isNotEmpty() && !isPasswordInvalid) {
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(250.dp),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Entrar")
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Ícone de seta",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

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