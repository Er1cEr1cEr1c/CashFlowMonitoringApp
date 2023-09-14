package com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.upgradedaccountingapp.R
import com.example.upgradedaccountingapp.common.utils.completeDateFormat
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.GetCashFlowRecordsViewModel
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.WholeListModeAction
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.WholeListModeResult
import com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation.WholeListModeState

@Composable
fun CashFlowListMode(
    dialog: WholeListModeResult?,
    viewModel: GetCashFlowRecordsViewModel,
    cashFlowRecords: WholeListModeState?
) {
    when (dialog) {
        is WholeListModeResult.DeleteCashFlowRecordsFail -> {
            DeleteFailDialogView({ viewModel.processWholeListModeAction(WholeListModeAction.DismissDialog) })
        }
        else -> {}
    }

    when (cashFlowRecords) {
        is WholeListModeState.AvailableRecordsToDisplay -> {
            val records = cashFlowRecords.records
            LazyColumn(Modifier.fillMaxSize()) {
                items(records) { record ->
                    val rowColor = if (record.incomeOrNot) Color.Green else Color.Gray
                    val rowIcon = when (record.category) {
                        1 -> R.drawable.heart_icon
                        2 -> R.drawable.driving_icon
                        3 -> R.drawable.food_and_drink_icon
                        else -> R.drawable.etc_icon
                    }
                    val dateTime = record.timestamp

                    Column(
                        modifier = Modifier.background(Color.LightGray),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = rowColor),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                modifier = Modifier.weight(1f),
                                painter = painterResource(rowIcon),
                                contentDescription = null
                            )
                            Column(
                                modifier = Modifier.weight(2f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = completeDateFormat.format(dateTime))
                                Text(text = "RM${record.amount}")
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    viewModel.processWholeListModeAction(
                                        WholeListModeAction.DeleteRecord(
                                            record
                                        )
                                    )
                                }
                            ) {
                                Text(text = "Delete")
                            }
                        }

                        Text(text = record.comment)
                    }

                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }

        is WholeListModeState.NoRecordsDisplayable -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Nothing here b0ss", fontSize = 40.sp)
            }
        }

        else -> {}
    }
}

@Composable
fun DeleteFailDialogView(onDismiss:() -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(10.dp)) {
                Text(text = "Deletion Failed", fontSize = 20.sp)
                Button(onClick = { onDismiss() }) { Text("Understood") }
            }
        }
    }
}