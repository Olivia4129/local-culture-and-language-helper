package com.olivia.localcultureandlanguagehelper.ui.theme.screens.Splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.olivia.localcultureandlanguagehelper.R
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_ONBOARDING
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_SPLASH
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreen(navController: NavHostController) {
    // ⏳ Navigate after delay
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(ROUTE_ONBOARDING) {
            popUpTo(ROUTE_SPLASH) { inclusive = true }
        }
    }

    // 🔹 Whole screen Box (no background image, no text)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // solid background color (can change or remove)
        contentAlignment = Alignment.Center
    ) {
        // Just App Logo centered
        Image(
            painter = painterResource(id = R.drawable.logo_culture),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit
        )
    }
}
