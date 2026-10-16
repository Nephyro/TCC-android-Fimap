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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    var usuario by remember { mutableStateOf("") }

    var senha by remember { mutableStateOf("") }

    var isFocusedPerson by remember { mutableStateOf(false) }

    var isFocusedSenha by remember { mutableStateOf(false) }


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
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Acesse sua conta para continuar")

        Spacer(modifier = Modifier.padding(20.dp))

        Card(
            modifier = Modifier
                .width(350.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF9F6F6)
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
                    value = usuario,
                    onValueChange = { usuario = it },
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
                            tint = if(isFocusedPerson) Color.Blue else Color.Gray
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Blue,
                        focusedBorderColor = Color.Blue
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.onFocusChanged { focusState -> isFocusedSenha = focusState.isFocused },
                    label =  {
                        Text(text = "Senha")
                    },
                    placeholder = {
                        Text(text = "Seu senha")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Ícone pessoa",
                            tint = if(isFocusedSenha) Color.Blue else Color.Gray
                        )
                    },

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = Color.Blue,
                        focusedBorderColor = Color.Blue
                    )
                )

                Text(
                    text = "Esqueci minha senha",
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("")
                        }
                )


                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )

                ) {
                    Row {
                        Text(text = "Entrar")
                    }
                }




            }
        }



    }
}