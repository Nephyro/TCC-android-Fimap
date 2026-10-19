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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fimap.R.drawable.fimap_logo

@Composable
fun CadastroScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }

    var cpf by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }

    var username by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }


    var isSubmitted by remember { mutableStateOf(false) }

    // foco dos icones
    var isFocusedPerson by remember { mutableStateOf(false) }
    var isFocusedId by remember { mutableStateOf(false) }
    var isFocusedEmail by remember { mutableStateOf(false) }
    var isFocusedUsername by remember { mutableStateOf(false) }
    var isFocusedPassword by remember { mutableStateOf(false) }

    var isNameInvalid by remember { mutableStateOf(false) }
    var isCPFInvalid by remember { mutableStateOf(false) }
    var isEmailInvalid by remember { mutableStateOf(false) }
    var isUsernameInvalid by remember { mutableStateOf(false) }
    var isPasswordInvalid by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current


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
                .size(100.dp),
            painter = painterResource(
                fimap_logo
            ),
            contentDescription = "logo app"
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Crie sua conta",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Cadastre-se para acesssar o Fimap",
            color = Color.Black
        )

        Spacer(modifier = Modifier.padding(5.dp))


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

                // CAMPO DE ENTRADA DO NOME
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (isSubmitted) isSubmitted = false
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedPerson = focusState.isFocused },
                    label =  {
                        Text(text = "Nome completo")
                    },
                    placeholder = {
                        Text(text = "Seu nome completo")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ícone pessoa",
                            tint = when{
                                isSubmitted && name.isEmpty() || isNameInvalid -> Color.Red
                                isFocusedPerson -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },
                    isError = isSubmitted && (name.isEmpty() || isNameInvalid),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    supportingText = {
                        if (isSubmitted && name.isEmpty()) {
                            Text(
                                text = "O campo nome não pode estar vazio",
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



                // CAMPO DE ENTRADA DO CPF
                OutlinedTextField(
                    value = cpf,
                    onValueChange = { input ->
                        val numbersOnly = input.filter { it.isDigit() }
                        if (numbersOnly.length <= 11) {
                            cpf = numbersOnly
                            if (isSubmitted) isSubmitted = false
                        }
                    },
                    visualTransformation = CpfVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedId = focusState.isFocused },
                    label =  {
                        Text(text = "CPF")
                    },
                    placeholder = {
                        Text(text = "000.000.000-00")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = "Ícone identidade",
                            tint = when{
                                isSubmitted && cpf.isEmpty() || isCPFInvalid -> Color.Red
                                isFocusedId -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },
                    isError = isSubmitted && (cpf.isEmpty() || isCPFInvalid),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    supportingText = {
                        if (isSubmitted && cpf.isEmpty()) {
                            Text(
                                text = "O campo cpf não pode estar vazio",
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



                // CAMPO DE ENTRADA DO EMAIL
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (isSubmitted) isSubmitted = false
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedEmail = focusState.isFocused },
                    label =  {
                        Text(text = "Email")
                    },
                    placeholder = {
                        Text(text = "seu@email.com")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Ícone email",
                            tint = when{
                                isSubmitted && email.isEmpty() || isEmailInvalid -> Color.Red
                                isFocusedEmail -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },
                    isError = isSubmitted && (email.isEmpty() || isEmailInvalid),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    supportingText = {
                        if (isSubmitted && name.isEmpty()) {
                            Text(
                                text = "O campo email não pode estar vazio",
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



                // CAMPO DE ENTRADA DO NOME DE USUÁRIO
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        if (isSubmitted) isSubmitted = false
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedUsername = focusState.isFocused },
                    label =  {
                        Text(text = "Username")
                    },
                    placeholder = {
                        Text(text = "Nome de usuário")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Ícone pessoa",
                            tint = when{
                                isSubmitted && username.isEmpty() || isUsernameInvalid -> Color.Red
                                isFocusedUsername -> Color.Blue
                                else -> Color.Gray
                            }
                        )
                    },
                    isError = isSubmitted && (username.isEmpty() || isUsernameInvalid),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),

                    supportingText = {
                        if (isSubmitted && username.isEmpty()) {
                            Text(
                                text = "O campo username não pode estar vazio",
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
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedPassword = focusState.isFocused },
                    label =  {
                        Text(text = "Senha")
                    },
                    placeholder = {
                        Text(text = "Mínimo 8 caracteres")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Ícone pessoa",
                            tint = when {
                                isSubmitted && (password.isEmpty() || isPasswordInvalid) -> Color.Red
                                isFocusedPassword -> Color.Blue
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
                                    isFocusedPassword -> Color.Blue
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

                    isError = isSubmitted && (password.isEmpty() || isPasswordInvalid || password.length < 8),

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







                Button(
                    onClick = {
                        isSubmitted = true

                        if (password.isNotEmpty() && password.length < 8) {
                            isPasswordInvalid = true
                        } else {
                            isPasswordInvalid = false
                        }

                        if (name.isNotEmpty() && password.isNotEmpty() && !isPasswordInvalid) {
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
                        Text(text = "Cadastrar")
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Ícone de seta",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }


                Row() {
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
                        modifier = Modifier
                            .clickable {
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


class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Pega apenas os primeiros 11 caracteres (caso passe algo a mais)
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        var out = ""

        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 2 || i == 5) out += "."
            if (i == 8) out += "-"
        }

        val cpfOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset + 1
                if (offset <= 8) return offset + 2
                if (offset <= 11) return offset + 3
                return 14
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset - 1
                if (offset <= 11) return offset - 2
                if (offset <= 14) return offset - 3
                return 11
            }
        }

        return TransformedText(AnnotatedString(out), cpfOffsetTranslator)
    }
}