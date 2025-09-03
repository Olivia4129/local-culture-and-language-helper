package com.olivia.localcultureandlanguagehelper.ui.screens.registerscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.olivia.localcultureandlanguagehelper.R
import com.olivia.localcultureandlanguagehelper.data.AuthViewModel

@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    // 🎨 Themed colors
    val deepRed = Color(0xFFB71C1C)
    val deepBlack = Color(0xFF121212)

    // Background gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(deepRed, deepBlack)
    )

    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "REGISTER",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.Blue,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🌟 Welcoming text moved below the main title
                Text(
                    text = "Welcome! Discover and enjoy different cultures",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Logo
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
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Name", tint = Color.White)
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
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password", tint = Color.White)
                    },
                    visualTransformation = PasswordVisualTransformation(),
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
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = Color.White) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirm Password", tint = Color.White)
                    },
                    visualTransformation = PasswordVisualTransformation(),
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
                        authViewModel.signup(name, email, password, confirmPassword)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = deepRed,
                        contentColor = Color.White
                    )
                ) {
                    Text("Register")
                }

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(error, color = Color.Yellow)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { navController.navigate("login") }) {
                    Text("Already have an account? Login", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}
