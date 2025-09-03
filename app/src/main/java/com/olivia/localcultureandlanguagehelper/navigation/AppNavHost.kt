package com.olivia.localcultureandlanguagehelper.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.olivia.localcultureandlanguagehelper.Onbording.OnboardingScreen

import com.olivia.localcultureandlanguagehelper.navigation.*
import com.olivia.localcultureandlanguagehelper.ui.screens.AddCultureScreen
import com.olivia.localcultureandlanguagehelper.ui.screens.loginscreen.LoginScreen
import com.olivia.localcultureandlanguagehelper.ui.screens.registerscreen.RegisterScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.CulturalFactsScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.DisplayCulturesScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.InteractiveQuizScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.LearnPhrasesScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.ProfileScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.culturescreen.UpdateCultureScreen
import com.olivia.localcultureandlanguagehelper.ui.theme.homescreen.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ONBOARDING
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController = navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController = navController)
        }
        composable(ROUTE_LEARNPHRASES) {
            LearnPhrasesScreen(navController = navController)
        }
        composable(ROUTE_CULTURALFACTS) {
            CulturalFactsScreen(navController = navController)
        }
        composable(ROUTE_INTERACTIVEQUIZ) {
            InteractiveQuizScreen(navController = navController)
        }
        composable(ROUTE_CULTURESCREEN) {
            AddCultureScreen(navController = navController)
        }
        composable(ROUTE_ADDCULTURE) {
            AddCultureScreen(navController = navController)
        }
        composable(ROUTE_VIEWCULTURE) {
            DisplayCulturesScreen(navController = navController)
        }
        composable("$ROUTE_UPDATECULTURE/{studentId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
            UpdateCultureScreen(navController, studentId)
        }
        composable(ROUTE_DELETECULTURE) {
            AddCultureScreen(navController = navController)
        }
        composable(ROUTE_ONBOARDING) {
            OnboardingScreen(navController = navController)
        }
        composable(ROUTE_USERPROFILE) {
            ProfileScreen(navController = navController)
        }

    }
}
