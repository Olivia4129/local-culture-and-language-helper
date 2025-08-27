package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

data class CulturalFact(
    val fact: String
)

data class TribeCulture(
    val name: String,
    val facts: List<CulturalFact>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturalFactsScreen(navController: NavHostController) {
    val tribesCulture = listOf(
        TribeCulture(
            name = "Kikuyu",
            facts = listOf(
                CulturalFact("The Kikuyu are the largest ethnic group in Kenya."),
                CulturalFact("Traditionally, they are farmers growing maize, beans, and bananas."),
                CulturalFact("They have a rich oral literature tradition, including proverbs and folk tales."),
                CulturalFact("Circumcision was traditionally a rite of passage for boys and girls."),
                CulturalFact("The Kikuyu worship a supreme god called Ngai, often associated with Mount Kenya.")
            )
        ),
        TribeCulture(
            name = "Luhya",
            facts = listOf(
                CulturalFact("The Luhya community is made up of several sub-tribes."),
                CulturalFact("They are known for traditional dances such as Isikuti."),
                CulturalFact("Farming is the main economic activity, especially maize and sugarcane."),
                CulturalFact("Initiation rites for boys and girls were traditional."),
                CulturalFact("They have a strong sense of community and clan structures.")
            )
        ),
        TribeCulture(
            name = "Luo",
            facts = listOf(
                CulturalFact("The Luo are Nilotic people living mainly around Lake Victoria."),
                CulturalFact("Fishing is a major livelihood activity."),
                CulturalFact("They have a rich musical tradition with instruments like the Nyatiti."),
                CulturalFact("The Luo are known for elaborate funeral rites."),
                CulturalFact("Patrilineal society with strong family ties.")
            )
        ),
        TribeCulture(
            name = "Kalenjin",
            facts = listOf(
                CulturalFact("The Kalenjin are famous for producing world-class long-distance runners."),
                CulturalFact("They are primarily pastoralists and farmers."),
                CulturalFact("Traditional ceremonies include rites of passage and initiation rituals."),
                CulturalFact("They live mainly in the Rift Valley region."),
                CulturalFact("Elders play a significant role in decision-making and conflict resolution.")
            )
        ),
        TribeCulture(
            name = "Kamba",
            facts = listOf(
                CulturalFact("The Kamba are known for their wood carving and basket weaving skills."),
                CulturalFact("They live mainly in Eastern Kenya."),
                CulturalFact("Farming and trade are their main livelihoods."),
                CulturalFact("They have rich oral traditions including proverbs and storytelling."),
                CulturalFact("The Kamba traditionally practice initiation rites for boys and girls.")
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kenyan Cultural Facts", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black // Black AppBar
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Red, Color.Black)
                    )
                )
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            tribesCulture.forEach { tribe ->
                item {
                    Text(
                        text = tribe.name,
                        fontSize = 20.sp,
                        color = Color.Red, // Tribe titles in Red
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    tribe.facts.forEach { fact ->
                        Text(
                            "• ${fact.fact}",
                            color = Color.White // Facts in White for contrast
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CulturalFactsScreenPreview() {
    CulturalFactsScreen(rememberNavController())
}
