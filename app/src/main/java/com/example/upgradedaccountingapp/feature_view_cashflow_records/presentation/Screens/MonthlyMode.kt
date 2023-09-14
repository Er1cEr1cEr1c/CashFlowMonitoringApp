package com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.SelectDateListener
import com.example.upgradedaccountingapp.R
import com.example.upgradedaccountingapp.common.utils.calendarObject
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.GetCashFlowRecordsViewModel
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.MonthlyModeAction
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.MonthlyModeResult
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.MonthlyModeState
import java.util.*

@Composable
fun CashFlowMonthlyMode(viewModel: GetCashFlowRecordsViewModel) {
    var dateSelected: Date? by remember { mutableStateOf(null) }
    var monthlyModeState = viewModel.monthlyModeState.observeAsState()
    var monthlyModeResult = viewModel.monthlyModeResult.observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when(monthlyModeResult.value) {
            is MonthlyModeResult.SelectDate -> MonthYearDatePicker(
                { dateSelected = it },
                { viewModel.processMonthlyModeAction(MonthlyModeAction.DismissDialog) }
            )

            else -> {}
        }

        //region Date Selection section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { viewModel.processMonthlyModeAction(MonthlyModeAction.ViewDatePickerDialog) }) {
                Text(text = "Pick month")
            }

            dateSelected?.let {
                calendarObject.setTime(it)
                val chosenMonth = calendarObject.get(Calendar.MONTH)
                val chosenYear = calendarObject.get(Calendar.YEAR)

                Text(
                    fontSize = 20.sp,
                    text = "${ chosenMonth + 1 }/${ chosenYear }"
                )
                viewModel.processMonthlyModeAction(MonthlyModeAction.PickMonthOfYear(chosenMonth, chosenYear))
            }
        }
        //endregion

        Column(
            Modifier
                .weight(9f)
                .padding(15.dp)
        ) {
            var fts by remember { mutableStateOf(0.0) }
            var foodAndDrinks by remember { mutableStateOf(0.0) }
            var drivingRelated by remember { mutableStateOf(0.0) }
            var miscellaneous by remember { mutableStateOf(0.0) }

            monthlyModeState.value.let {
                when(it) {
                    is MonthlyModeState.SuccessfulCalculation -> {
                        fts = it.fts
                        foodAndDrinks = it.foodAndDrinks
                        drivingRelated = it.drivingRelated
                        miscellaneous = it.miscellaneous
                    }

                    else -> {
                        fts = -1.0
                        foodAndDrinks = -1.0
                        drivingRelated = -1.0
                        miscellaneous = -1.0
                    }
                }
            }

            ExpenseCategoryRow(icon = R.drawable.heart_icon, fts)
            ExpenseCategoryRow(icon = R.drawable.food_and_drink_icon, foodAndDrinks)
            ExpenseCategoryRow(icon = R.drawable.driving_icon, drivingRelated)
            ExpenseCategoryRow(icon = R.drawable.etc_icon, miscellaneous)
        }
    }
}

@Composable
fun ExpenseCategoryRow(icon:Int, totalMonthlySpending: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.weight(1f),
            painter = painterResource(icon),
            contentDescription = null
        )

        Text(
            modifier = Modifier.weight(1f),
            fontSize = 20.sp,
            text = "monthly total: "
        )
        Text(
            modifier = Modifier.weight(1f),
            fontSize = 20.sp,
            text = totalMonthlySpending.toString()
        )
    }
}

@Composable
fun MonthYearDatePicker(
    dateSelected: (date: Date) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        ComposeCalendar(
            locale = Locale("en"),
            title = "Select Date",
            listener = object : SelectDateListener {
                override fun onDateSelected(date: Date) {
                    dateSelected(date)
                    onDismiss()
                }
                override fun onCanceled() { onDismiss() }
            }
        )
    }
}