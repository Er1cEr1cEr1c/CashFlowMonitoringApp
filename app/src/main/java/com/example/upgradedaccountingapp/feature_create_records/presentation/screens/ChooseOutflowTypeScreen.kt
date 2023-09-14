package com.example.upgradedaccountingapp.feature_create_records.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.upgradedaccountingapp.R
import com.example.upgradedaccountingapp.common.presentation.ScreenSealedClass

@Composable
fun ChooseOutFlowTypeScreen(
    navController: NavController,
    viewModel: InsertCashFlowRecordViewModel = hiltViewModel()
) {
    val viewResult = viewModel.viewResult.observeAsState()

    val category = 0

    Column(Modifier.fillMaxSize()) {
        when(viewResult.value) {
            is ChosenExpensesTypeScreenResult.DisplayInputDialog -> {
                RecordInputDialogView(
                    category,
                    onDismiss = { viewModel.processAction(ChosenExpensesTypeScreenAction.RemoveDialog) },
                    onConfirm = { category, amount, comment ->
                        viewModel.processAction(
                            ChosenExpensesTypeScreenAction.CreateRecord(
                                (viewResult.value as ChosenExpensesTypeScreenResult.DisplayInputDialog).category,
                                amount,
                                comment
                            )
                        )
                    }
                )
            }

            is ChosenExpensesTypeScreenResult.SuccessfulEntry -> {
                navController.popBackStack(route = ScreenSealedClass.MainMenuScreen.route, false)
            }

            is ChosenExpensesTypeScreenResult.ErrorOccurred -> {
                InsertionFailDialogView() {
                    viewModel.processAction(ChosenExpensesTypeScreenAction.RemoveDialog)
                }
            }

            is ChosenExpensesTypeScreenResult.RemoveDialog -> {}

            else -> {}
        }

        Row(Modifier
                .fillMaxWidth()
                .weight(1f)) {
            OutFlowTypeOption(
                Modifier.weight(1f),
                "For The Soul",
                iconId = R.drawable.heart_icon,
                onClick = {
                    viewModel.processAction(ChosenExpensesTypeScreenAction.ChooseCategory(1))
                }
            )
            OutFlowTypeOption(
                Modifier.weight(1f),
                "Driving Related",
                iconId = R.drawable.driving_icon,
                onClick = {
                    viewModel.processAction(ChosenExpensesTypeScreenAction.ChooseCategory(2))
                }
            )
        }

        Row(Modifier
                .fillMaxWidth()
                .weight(1f)) {
            OutFlowTypeOption(
                Modifier.weight(1f),
                "Food and Drink",
                iconId = R.drawable.food_and_drink_icon,
                onClick = {
                    viewModel.processAction(ChosenExpensesTypeScreenAction.ChooseCategory(3))
                }
            )
            OutFlowTypeOption(
                Modifier.weight(1f),
                "Miscellaneous",
                iconId = R.drawable.etc_icon,
                onClick = {
                    viewModel.processAction(ChosenExpensesTypeScreenAction.ChooseCategory(4))
                }
            )
        }
    }
}

@Composable
fun OutFlowTypeOption(
    modifier: Modifier,
    title: String,
    iconId: Int,
    onClick:() -> Unit
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center,
                text = title
            )
        }
    }
}

@Composable
fun RecordInputDialogView(
    chosenExpenseCategory: Int,
    onConfirm:(category: Int, amount: String, comment: String) -> Unit,
    onDismiss:() -> Unit
) {
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
                val amount = remember{ mutableStateOf("") }
                Text(text = "Amount : ")
                OutlinedTextField(
                    value = amount.value,
                    onValueChange = { amount.value = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                val comment = remember{ mutableStateOf("") }
                Text(text = "Comments: ")
                OutlinedTextField(
                    value = comment.value,
                    onValueChange = { comment.value = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { onConfirm(chosenExpenseCategory, amount.value, comment.value) }) {
                        Text(text = "Enter Record")
                    }
                }
            }
        }
    }
}

@Composable
fun InsertionFailDialogView(onDismiss:() -> Unit) {
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
                Text(text = "Insertion Failed", fontSize = 20.sp)
                Button(onClick = { onDismiss() }) { Text("Understood") }
            }
        }
    }
}