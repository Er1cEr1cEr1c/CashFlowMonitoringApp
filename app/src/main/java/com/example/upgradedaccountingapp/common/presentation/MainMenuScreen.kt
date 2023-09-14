package com.example.upgradedaccountingapp.feature_create_records.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.upgradedaccountingapp.common.presentation.ScreenSealedClass

@Composable
fun MainMenuScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(ScreenSealedClass.ChooseOutflowTypeScreen.route) }) {
            Text(text = "Create new entry")
        }

        Button(onClick = { navController.navigate(ScreenSealedClass.CashFlowListScreen.route) }) {
            Text(text = "View entries")
        }
    }
}