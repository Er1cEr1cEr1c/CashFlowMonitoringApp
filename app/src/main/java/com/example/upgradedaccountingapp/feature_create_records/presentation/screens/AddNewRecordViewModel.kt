package com.example.upgradedaccountingapp.feature_create_records.presentation.screens

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.upgradedaccountingapp.CashFlowMonitoringApp
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.common.utils.setValueOnMainThread
import com.example.upgradedaccountingapp.feature_create_records.domain.use_case.InsertCashFlowRecord_UC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.upgradedaccountingapp.common.utils.Result

@HiltViewModel
class InsertCashFlowRecordViewModel @Inject constructor(
    private val insertCashFlowRecordUc: InsertCashFlowRecord_UC,
    savedStateHandle: SavedStateHandle
): AndroidViewModel(CashFlowMonitoringApp()) {

    val _viewResult:MutableLiveData<ChosenExpensesTypeScreenResult> = MutableLiveData(ChosenExpensesTypeScreenResult.RemoveDialog)
    val viewResult: LiveData<ChosenExpensesTypeScreenResult> = _viewResult

    fun processAction(action: ChosenExpensesTypeScreenAction) {
        when(action) {
            is ChosenExpensesTypeScreenAction.ChooseCategory -> {
                _viewResult.setValueOnMainThread(ChosenExpensesTypeScreenResult.DisplayInputDialog(action.category))
            }

            is ChosenExpensesTypeScreenAction.CreateRecord -> {
                addNewCashFlowRecord(action.category, action.amount, action.comment)
            }

            is ChosenExpensesTypeScreenAction.RemoveDialog -> {
                _viewResult.setValueOnMainThread(ChosenExpensesTypeScreenResult.RemoveDialog)
            }
        }
    }

    fun addNewCashFlowRecord(category: Int, amount: String, comment: String) {
        viewModelScope.launch(IO) {
            try {
                val result = insertCashFlowRecordUc(
                    CashFlowRecord(
                        timestamp = System.currentTimeMillis(),
                        incomeOrNot = false,
                        category = category,
                        amount = amount.toDouble(),
                        comment = comment
                    )
                )

                when(result) {
                    is Result.Success -> _viewResult.setValueOnMainThread(ChosenExpensesTypeScreenResult.SuccessfulEntry)
                    else -> _viewResult.setValueOnMainThread(ChosenExpensesTypeScreenResult.ErrorOccurred)
                }
            }
            catch (e: java.lang.Exception) {
                _viewResult.setValueOnMainThread(ChosenExpensesTypeScreenResult.ErrorOccurred)
            }
        }
    }
}

sealed class ChosenExpensesTypeScreenAction() {
    data class ChooseCategory(var category: Int): ChosenExpensesTypeScreenAction()
    data class CreateRecord(var category: Int, var amount: String, var comment: String): ChosenExpensesTypeScreenAction()
    object RemoveDialog: ChosenExpensesTypeScreenAction()
}

sealed class ChosenExpensesTypeScreenResult() {
    object SuccessfulEntry: ChosenExpensesTypeScreenResult()
    object ErrorOccurred: ChosenExpensesTypeScreenResult()
    data class DisplayInputDialog(val category: Int): ChosenExpensesTypeScreenResult()
    object RemoveDialog: ChosenExpensesTypeScreenResult()
}