package com.olivia.localcultureandlanguagehelper.ui.theme.screens.Splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.olivia.localcultureandlanguagehelper.R
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_ONBOARDING
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_SPLASH
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(true) {
        delay(2000) // 2-second splash duration
        navController.navigate(ROUTE_ONBOARDING) {
            popUpTo(ROUTE_SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.flag),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        // 🔹 Foreground Content (Logo + Text)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_culture),
                contentDescription = "App Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Welcome to my app",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
