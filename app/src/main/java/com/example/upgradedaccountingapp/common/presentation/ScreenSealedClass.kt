package com.example.upgradedaccountingapp.common.presentation

sealed class ScreenSealedClass(val route: String) {
    object MainMenuScreen: ScreenSealedClass("main_menu")
    object ChooseOutflowTypeScreen: ScreenSealedClass("choose_outflow_type_screen")
    object CashFlowListScreen: ScreenSealedClass("cash_flow_list_screen")
}

