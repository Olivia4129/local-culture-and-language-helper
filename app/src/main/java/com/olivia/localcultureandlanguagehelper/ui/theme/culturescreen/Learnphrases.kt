package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.shape.RoundedCornerShape

data class Phrase(
    val english: String,
    val translation: String
)

data class Tribe(
    val name: String,
    val phrases: List<Phrase>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnPhrasesScreen(navController: NavHostController) {
    val tribes = listOf(
        Tribe(
            name = "Kikuyu",
            phrases = listOf(
                Phrase("Hello", "Wimwega"),
                Phrase("How are you?", "Uhana atia?"),
                Phrase("Thank you", "Thengio"),
                Phrase("Yes", "Emi"),
                Phrase("No", "Aca")
            )
        ),
        Tribe(
            name = "Luhya",
            phrases = listOf(
                Phrase("Hello", "Bwakire Buya"),
                Phrase("How are you?", "Naki?"),
                Phrase("Thank you", "Webale"),
                Phrase("Yes", "Ee"),
                Phrase("No", "Ndi")
            )
        ),
        Tribe(
            name = "Luo",
            phrases = listOf(
                Phrase("Hello", "wachna"),
                Phrase("How are you?", "idhi nade?"),
                Phrase("Thank you", "erokamano"),
                Phrase("Yes", "Ee"),
                Phrase("No", "adagi")
            )
        ),
        Tribe(
            name = "Kalenjin",
            phrases = listOf(
                Phrase("Hello", "Chebo"),
                Phrase("How are you?", "Chepyos?"),
                Phrase("Thank you", "kongoi mising"),
                Phrase("Yes", "Eyo"),
                Phrase("No", "Bore")
            )
        ),
        Tribe(
            name = "Kamba",
            phrases = listOf(
                Phrase("Hello", "Wîîhîî"),
                Phrase("How are you?", "Wiî mwe?"),
                Phrase("Thank you", "Nîkwî"),
                Phrase("Yes", "Ee"),
                Phrase("No", "Hîî")
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learn Kenyan Phrases", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB71C1C) // Deep Red top bar
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black) // Black background
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            tribes.forEach { tribe ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFD32F2F) // Red card
                        ),
                        shape = RoundedCornerShape(12.dp), // Rounded corners
                        elevation = CardDefaults.cardElevation(12.dp) // Increased shadow
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = tribe.name,
                                fontSize = 20.sp,
                                color = Color.White, // Tribe name white
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            tribe.phrases.forEach { phrase ->
                                Text(
                                    text = "${phrase.english} → ${phrase.translation}",
                                    color = Color.White // Phrase text white
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LearnPhrasesScreenPreview() {
    LearnPhrasesScreen(rememberNavController())
}
