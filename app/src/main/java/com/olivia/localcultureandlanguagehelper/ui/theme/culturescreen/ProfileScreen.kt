package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.olivia.localcultureandlanguagehelper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Profile", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F), // Red top bar
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFD32F2F), // Red
                            Color.Black          // Black
                        )
                    )
                )
        ) {
            // 👤 Profile content on top
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.culture), // Your profile picture
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Olivia Akoth",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = "olivia.akoth.arodi@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFFF59D) // soft yellow for contrast
                )
            }
        }
    }
}
@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}
