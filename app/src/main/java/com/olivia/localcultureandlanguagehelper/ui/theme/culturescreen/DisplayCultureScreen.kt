package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.olivia.localcultureandlanguagehelper.data.CultureViewModel
import com.olivia.localcultureandlanguagehelper.models.Culture
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_ADDCULTURE
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_DISPLAYCULTURE
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_HOME
import com.olivia.localcultureandlanguagehelper.navigation.ROUTE_UPDATECULTURE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayCulturesScreen(navController: NavHostController) {
    val culture = remember { mutableStateOf(Culture("", "", "", "")) }
    val cultures = remember { mutableStateListOf<Culture>() }
    val context = LocalContext.current
    val cultureViewModel = CultureViewModel(navController, context)

    // Load cultures
    LaunchedEffect(Unit) {
        cultureViewModel.allCultures(culture, cultures)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Culture List", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF000000) // black top bar
                )
            )
        },
        bottomBar = {
            BottomCultureNavigationBar(navController)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Red, Color.Black)
                    )
                )
        ) {
            items(cultures) { cult ->
                CultureItem(
                    culture = cult,
                    onEdit = { navController.navigate("$ROUTE_UPDATECULTURE/${cult.id}") },
                    onDelete = { cultureViewModel.deleteCulture(cult.id!!) }
                )
            }
        }
    }
}

@Composable
fun CultureItem(
    culture: Culture,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Culture Image
            if (culture.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(culture.imageUrl),
                    contentDescription = "Culture Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Culture Info
            Text(culture.name, style = MaterialTheme.typography.titleMedium, color = Color.Black)
            Text(culture.description, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)

            Spacer(modifier = Modifier.height(12.dp))

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onEdit() }
                )
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}

@Composable
fun BottomCultureNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate(ROUTE_HOME) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
            label = { Text("Add") },
            selected = false,
            onClick = { navController.navigate(ROUTE_ADDCULTURE) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "List") },
            label = { Text("List") },
            selected = false,
            onClick = { navController.navigate(ROUTE_DISPLAYCULTURE) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CultureListPreview() {
    DisplayCulturesScreen(rememberNavController())
}
