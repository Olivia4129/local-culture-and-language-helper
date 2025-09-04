package com.olivia.localcultureandlanguagehelper.Onbording

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.olivia.localcultureandlanguagehelper.data.onboardingItems
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_LOGIN
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_ONBOARDING
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_REGISTER
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { onboardingItems.size })
    val scope = rememberCoroutineScope()

    // ✅ Apply red → black gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.Black)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ Skip button
            if (pagerState.currentPage != onboardingItems.lastIndex) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate(ROUTE_LOGIN) {
                                popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                            }
                        }
                    ) {
                        Text("Skip", color = Color.White)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(48.dp))
            }

            // ✅ Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val item = onboardingItems[page]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,   // ✅ ensure cropped nicely
                        modifier = Modifier
                            .size(200.dp)                  // ✅ force square
                            .clip(CircleShape)             // ✅ make perfectly round
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFFFFC107),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Yellow,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ Custom indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(onboardingItems.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (isSelected) 12.dp else 8.dp)
                            .background(
                                if (isSelected) Color.White
                                else Color.Gray.copy(alpha = 0.6f),
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }

            // ✅ Next / Get Started button
            Button(
                onClick = {
                    if (pagerState.currentPage == onboardingItems.lastIndex) {
                        navController.navigate(ROUTE_REGISTER) {
                            popUpTo(ROUTE_ONBOARDING) { inclusive = true }
                        }
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingItems.lastIndex) "Get Started" else "Next"
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
