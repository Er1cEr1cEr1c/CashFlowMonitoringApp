package com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation

import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.Screens.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CashFlowRecordsScreen(
    navController: NavController,
    viewModel: GetCashFlowRecordsViewModel = hiltViewModel()
){
    val cashFlowRecords by viewModel.wholeListModeState.observeAsState()
    val dialogResult by viewModel.wholeListModeResult.observeAsState()

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Monthly Summary", "Whole List")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> CashFlowMonthlyMode(viewModel)
            1 -> CashFlowListMode(dialogResult, viewModel, cashFlowRecords)
        }
    }
}