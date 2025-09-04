package com.olivia.localcultureandlanguagehelper.data

import com.olivia.localcultureandlanguagehelper.R

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageRes: Int
)
val onboardingItems = listOf(
    OnboardingItem(
        title = "Welcome to Local Culture and Language Helper App",
        description = "Discover Kenyan's heritage through language traditions and culture.",
        imageRes = R.drawable.flag
    ),
    OnboardingItem(
        title = "For Cultures",
        description = "Upload for foods, music and art.",
        imageRes = R.drawable.cultural
    ),
    OnboardingItem(
        title = "For Viewers",
        description = "View ceremonies, traditions and believe of language, and check results.",
        imageRes = R.drawable.fishing
    )
)

