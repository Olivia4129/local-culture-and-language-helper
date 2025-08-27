package com.olivia.localcultureandlanguagehelper.models


data class Phrase(
    val english: String,
    val translation: String
)

data class CulturalFact(
    val fact: String
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

data class Tribe(
    val name: String,
    val phrases: List<Phrase> = emptyList(),
    val culturalFacts: List<CulturalFact> = emptyList(),
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val imageUrl: String? = null // optional, for a small tribe icon
)
data class Culture(
    var id: String = "",
    val name: String = "",
    val tribe: String = "",
    val description: String = "",
    val event: String = "",
    val imageUrl: String = "",
    //val userId: String = ""   // ✅ add back
)

