package com.olivia.localcultureandlanguagehelper.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.olivia.localcultureandlanguagehelper.R
import com.olivia.localcultureandlanguagehelper.data.CultureViewModel
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_HOME
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_ADDCULTURE
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_VIEWCULTURE
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_CULTURALFACTS   // 🔹 Added

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCultureScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var tribe by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var event by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val context = LocalContext.current
    val cultureViewModel = CultureViewModel(navController, context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Culture", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red
                ),
                actions = {
                    IconButton(
                        onClick = {
                            // 🔹 Navigate to Cultural Facts when search icon is clicked
                            navController.navigate(ROUTE_CULTURALFACTS)
                        }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Go to Cultural Facts",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                val navItems = listOf("Home", "Add Culture", "Culture List")
                val navIcons = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Menu)
                val navRoutes = listOf(ROUTE_HOME, ROUTE_ADDCULTURE, ROUTE_VIEWCULTURE)

                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(navIcons[index], contentDescription = item, tint = Color.White)
                        },
                        label = { Text(item, color = Color.White) },
                        selected = currentRoute == navRoutes[index],
                        onClick = {
                            navController.navigate(navRoutes[index]) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Red, Color.Black)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Add New Culture",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .size(140.dp)
                    .clickable { imagePickerLauncher.launch("image/*") }
                    .shadow(8.dp, CircleShape)
            ) {
                AsyncImage(
                    model = imageUri ?: R.drawable.logo_culture,
                    contentDescription = "Culture Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(140.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { imagePickerLauncher.launch("image/*") },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Choose Culture Image")
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Culture Name", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = tribe,
                onValueChange = { tribe = it },
                label = { Text("Tribe", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = event,
                onValueChange = { event = it },
                label = { Text("Event / Festival", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    cultureViewModel.uploadCulture(
                        imageUri = imageUri,
                        name = name,
                        tribe = tribe,
                        description = description,
                        event = event
                    )
                    name = ""
                    tribe = ""
                    description = ""
                    event = ""
                    imageUri = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Add Culture")
            }
        }
    }
}

@Preview
@Composable
fun AddCultureScreenPreview() {
    AddCultureScreen(navController = rememberNavController())
}
