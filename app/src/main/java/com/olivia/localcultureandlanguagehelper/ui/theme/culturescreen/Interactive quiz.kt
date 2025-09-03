package com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveQuizScreen(navController: NavHostController) {
    val questions = listOf(
        QuizQuestion(
            question = "How do you say 'Hello' in Kikuyu?",
            options = listOf("Wîhîî", "Bwakire Buya", "Ongera", "Wîîhîî"),
            correctAnswer = "Wîhîî"
        ),
        QuizQuestion(
            question = "Which tribe is famous for long-distance runners?",
            options = listOf("Luhya", "Kalenjin", "Luo", "Kamba"),
            correctAnswer = "Kalenjin"
        ),
        QuizQuestion(
            question = "The Luo mainly live near which lake?",
            options = listOf("Lake Victoria", "Lake Turkana", "Lake Naivasha", "Lake Bogoria"),
            correctAnswer = "Lake Victoria"
        ),
        QuizQuestion(
            question = "Which tribe is known for wood carving and basket weaving?",
            options = listOf("Kamba", "Kikuyu", "Luhya", "Luo"),
            correctAnswer = "Kamba"
        ),
        QuizQuestion(
            question = "What is 'Thank you' in Luhya?",
            options = listOf("Aheri", "Webale", "Thengî", "Nîkwî"),
            correctAnswer = "Webale"
        )
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var score by remember { mutableStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentQuestionIndex]

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFB71C1C), Color.Black) // red to black gradient
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kenyan Tribes Quiz", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!showResult) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF8B0000), // Darker red card
                        contentColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                            fontSize = 16.sp,
                            color = Color.LightGray
                        )
                        Text(
                            text = currentQuestion.question,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )

                        currentQuestion.options.forEach { option ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (option == selectedOption),
                                        onClick = { selectedOption = option }
                                    )
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = option == selectedOption,
                                    onClick = { selectedOption = option },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color.LightGray
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(option, color = Color.White)
                            }
                        }

                        Button(
                            onClick = {
                                if (selectedOption == currentQuestion.correctAnswer) {
                                    score++
                                }
                                if (currentQuestionIndex < questions.size - 1) {
                                    currentQuestionIndex++
                                    selectedOption = null
                                } else {
                                    showResult = true
                                }
                            },
                            enabled = selectedOption != null,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Text(if (currentQuestionIndex < questions.size - 1) "Next" else "Finish")
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 Quiz Completed!",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFFFF5252) // bright red accent
                        )
                        Text(
                            text = "Your Score: $score / ${questions.size}",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Button(
                            onClick = {
                                currentQuestionIndex = 0
                                selectedOption = null
                                score = 0
                                showResult = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB71C1C),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Restart Quiz")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InteractiveQuizScreenPreview() {
    InteractiveQuizScreen(rememberNavController())
}
