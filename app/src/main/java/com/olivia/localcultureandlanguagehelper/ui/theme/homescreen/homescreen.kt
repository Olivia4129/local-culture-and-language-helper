package com.olivia.localcultureandlanguagehelper.ui.theme.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.olivia.localcultureandlanguagehelper.data.AuthViewModel
import com.olivia.localcultureandlanguagehelper.navigation.*

/* 🎨 Updated theme colors */
private val TopBarColor = Color(0xFFD32F2F)   // Deep Red
private val BottomBarColor = Color(0xFF212121) // Dark Gray/Almost Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    // ✅ Bottom Nav Items (removed "List")
    val navItems = listOf("Home", "Learn", "Facts", "Quiz",)
    val navIcons = listOf(
        Icons.Default.Home,
        Icons.Default.Person,
        Icons.Default.Settings,
        Icons.Default.ExitToApp // temporary for quiz
    )
    val navRoutes = listOf(
        ROUTE_HOME,
        ROUTE_LEARNPHRASES,
        ROUTE_CULTURALFACTS,
        ROUTE_INTERACTIVEQUIZ
    )

    var selectedIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val authViewModel = AuthViewModel(navController, context)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Culture & Language Helper") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarColor,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_USERPROFILE) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = { navController.navigate(ROUTE_SETTINGS) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = BottomBarColor, contentColor = Color.White) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(navIcons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(navRoutes[index])
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { authViewModel.logout() },
                containerColor = TopBarColor, // Match topbar red
                contentColor = Color.White
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }
    ) { innerPadding ->
        // 🔴⚫ Red–Black Gradient Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFD32F2F), Color.Black) // Red → Black
                    )
                )
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    HomeCard(
                        title = "Learn Phrases",
                        description = "Translate and listen to words in multiple languages",
                        backgroundColor = Color(0xFF81D4FA),
                        onClick = { navController.navigate(ROUTE_LEARNPHRASES) }
                    )
                }
                item {
                    HomeCard(
                        title = "Interactive Quiz",
                        description = "Test your knowledge of languages and culture",
                        backgroundColor = Color(0xFFAED581),
                        onClick = { navController.navigate(ROUTE_INTERACTIVEQUIZ) }
                    )
                }
                item {
                    HomeCard(
                        title = "Cultural Facts",
                        description = "Learn interesting facts about different communities",
                        backgroundColor = Color(0xFFCE93D8),
                        onClick = { navController.navigate(ROUTE_CULTURALFACTS) }
                    )
                }
                item {
                    HomeCard(
                        title = "Add Culture",
                        description = "Events, festivals, cultural facts & community uploads",
                        backgroundColor = Color(0xFFFFF59D),
                        onClick = { navController.navigate(ROUTE_ADDCULTURE) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    description: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
