package com.kindkrank.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kindkrank.app.ui.screens.DisclaimerScreen
import com.kindkrank.app.ui.screens.GrenzgaengerSchweizScreen
import com.kindkrank.app.ui.screens.HomeScreen
import com.kindkrank.app.ui.screens.SpecialCasesScreen
import com.kindkrank.app.ui.screens.wizard.Step1FamilySituation
import com.kindkrank.app.ui.screens.wizard.Step2Krankenkasse
import com.kindkrank.app.ui.screens.wizard.Step3Doctor
import com.kindkrank.app.ui.screens.wizard.Step4EmployerAndInsurance

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Disclaimer.route,
    ) {
        composable(Screen.Disclaimer.route) {
            DisclaimerScreen(
                onAccepted = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Disclaimer.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onStartWizard = { navController.navigate(Screen.Step1FamilySituation.route) },
                onSpecialCases = { navController.navigate(Screen.SpecialCases.route) },
            )
        }

        composable(Screen.Step1FamilySituation.route) {
            Step1FamilySituation(
                onNext = { isSingleParent ->
                    navController.navigate(Screen.Step2Krankenkasse.createRoute(isSingleParent))
                },
                onBack = { navController.popBackStack() },
                onGrenzgaenger = { navController.navigate(Screen.GrenzgaengerSchweiz.route) },
            )
        }

        composable(
            route = Screen.Step2Krankenkasse.route,
            arguments = listOf(navArgument("isSingleParent") { type = NavType.BoolType }),
        ) { backStackEntry ->
            val isSingleParent = backStackEntry.arguments?.getBoolean("isSingleParent") ?: false
            Step2Krankenkasse(
                isSingleParent = isSingleParent,
                onNext = { krankenkasse ->
                    navController.navigate(Screen.Step3Doctor.createRoute(isSingleParent, krankenkasse))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.Step3Doctor.route,
            arguments = listOf(
                navArgument("isSingleParent") { type = NavType.BoolType },
                navArgument("krankenkasse") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val isSingleParent = backStackEntry.arguments?.getBoolean("isSingleParent") ?: false
            val krankenkasse = backStackEntry.arguments?.getString("krankenkasse") ?: "other"
            Step3Doctor(
                isSingleParent = isSingleParent,
                krankenkasse = krankenkasse,
                onNext = {
                    navController.navigate(
                        Screen.Step4EmployerAndInsurance.createRoute(isSingleParent, krankenkasse)
                    )
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.Step4EmployerAndInsurance.route,
            arguments = listOf(
                navArgument("isSingleParent") { type = NavType.BoolType },
                navArgument("krankenkasse") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val isSingleParent = backStackEntry.arguments?.getBoolean("isSingleParent") ?: false
            val krankenkasse = backStackEntry.arguments?.getString("krankenkasse") ?: "other"
            Step4EmployerAndInsurance(
                isSingleParent = isSingleParent,
                krankenkasse = krankenkasse,
                onGoHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onSpecialCases = { navController.navigate(Screen.SpecialCases.route) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Screen.SpecialCases.route) {
            SpecialCasesScreen(
                onGoHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Screen.GrenzgaengerSchweiz.route) {
            GrenzgaengerSchweizScreen(
                onGoHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
