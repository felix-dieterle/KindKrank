package com.kindkrank.app.navigation

sealed class Screen(val route: String) {
    data object Disclaimer : Screen("disclaimer")
    data object Home : Screen("home")
    data object Step1FamilySituation : Screen("step1")
    data object Step2Krankenkasse : Screen("step2/{isSingleParent}") {
        fun createRoute(isSingleParent: Boolean) = "step2/$isSingleParent"
    }
    data object Step3Doctor : Screen("step3/{isSingleParent}/{krankenkasse}") {
        fun createRoute(isSingleParent: Boolean, krankenkasse: String) =
            "step3/$isSingleParent/$krankenkasse"
    }
    data object Step4EmployerAndInsurance : Screen("step4/{isSingleParent}/{krankenkasse}") {
        fun createRoute(isSingleParent: Boolean, krankenkasse: String) =
            "step4/$isSingleParent/$krankenkasse"
    }
    data object SpecialCases : Screen("special_cases")
    data object GrenzgaengerSchweiz : Screen("grenzgaenger_schweiz")
}
