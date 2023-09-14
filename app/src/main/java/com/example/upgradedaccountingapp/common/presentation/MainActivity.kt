package com.example.upgradedaccountingapp.common.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.upgradedaccountingapp.feature_create_records.presentation.screens.ChooseOutFlowTypeScreen
import com.example.upgradedaccountingapp.feature_create_records.presentation.screens.MainMenuScreen
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.CashFlowRecordsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = ScreenSealedClass.MainMenuScreen.route
            ) {
                composable(route = ScreenSealedClass.MainMenuScreen.route) {
                    MainMenuScreen(navController = navController)
                }

                composable(route = ScreenSealedClass.ChooseOutflowTypeScreen.route) {
                    ChooseOutFlowTypeScreen(navController = navController)
                }

                composable(route = ScreenSealedClass.CashFlowListScreen.route) {
                    CashFlowRecordsScreen(navController = navController)
                }
            }
        }
    }
}
