package com.olivia.localcultureandlanguagehelper.ui.theme.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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

// 🎨 Theme colors
private val TopBarColor = Color(0xFFD32F2F)   // Deep Red
private val BottomBarColor = Color(0xFF121212) // Darker background

// 🎨 Card gradient brushes
private val CardGradient1 = Brush.verticalGradient(listOf(Color(0xFFD32F2F), Color.Black))
private val CardGradient2 = Brush.verticalGradient(listOf(Color(0xFFE53935), Color(0xFF212121)))
private val CardGradient3 = Brush.verticalGradient(listOf(Color(0xFFB71C1C), Color(0xFF000000)))
private val CardGradient4 = Brush.verticalGradient(listOf(Color(0xFFFF5252), Color(0xFF1C1C1C)))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val navItems = listOf("Home", "Learn", "Facts", "Quiz")
    val navIcons = listOf(
        Icons.Default.Home,
        Icons.Default.Person,
        Icons.Default.ExitToApp,
        Icons.Default.Edit
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

    // 🔹 State for logout confirmation dialog
    var showLogoutDialog by remember { mutableStateOf(false) }

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
                onClick = { showLogoutDialog = true }, // Show confirmation dialog
                containerColor = TopBarColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Black, Color(0xFFB71C1C))
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
                        backgroundBrush = CardGradient1,
                        onClick = { navController.navigate(ROUTE_LEARNPHRASES) }
                    )
                }
                item {
                    HomeCard(
                        title = "Interactive Quiz",
                        description = "Test your knowledge of languages and culture",
                        backgroundBrush = CardGradient2,
                        onClick = { navController.navigate(ROUTE_INTERACTIVEQUIZ) }
                    )
                }
                item {
                    HomeCard(
                        title = "Cultural Facts",
                        description = "Learn interesting facts about different communities",
                        backgroundBrush = CardGradient3,
                        onClick = { navController.navigate(ROUTE_CULTURALFACTS) }
                    )
                }
                item {
                    HomeCard(
                        title = "Add Culture",
                        description = "Events, festivals, cultural facts & community uploads",
                        backgroundBrush = CardGradient4,
                        onClick = { navController.navigate(ROUTE_ADDCULTURE) }
                    )
                }
            }
        }
    }

    // 🔹 Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        authViewModel.logout() // Perform logout
                    }
                ) {
                    Text("Yes, Logout", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to logout from the app?") }
        )
    }
}

@Composable
fun HomeCard(
    title: String,
    description: String,
    backgroundBrush: Brush,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundBrush)
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
