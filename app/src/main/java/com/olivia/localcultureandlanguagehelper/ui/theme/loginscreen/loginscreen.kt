package com.olivia.localcultureandlanguagehelper.ui.screens.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.olivia.localcultureandlanguagehelper.R
import com.olivia.localcultureandlanguagehelper.data.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    // 🎨 Smart colors
    val deepRed = Color(0xFFB71C1C)
    val deepBlack = Color(0xFF121212)

    // Gradient Background Brush
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(deepRed, deepBlack)
    )

    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(3.dp), // 🔹 Thinner, smarter shadow
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f)) // subtle glass effect
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 🔹 LOGIN Title
                Text(
                    text = "LOGIN",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic
                    ),
                    color = Color.Blue,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ✅ Rounded Logo
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_culture),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.White)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = deepRed,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = deepRed,
                        cursorColor = deepRed,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.White) },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password", tint = Color.White)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = deepRed,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = deepRed,
                        cursorColor = deepRed,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            authViewModel.login(email, password)
                        } else {
                            error = "Please fill in all fields"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = deepRed,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login")
                }

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(error, color = Color.Yellow)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { navController.navigate("register") }) {
                    Text("Don't have an account? Register", color = Color.White)
                }
            }
        }
    }
}
