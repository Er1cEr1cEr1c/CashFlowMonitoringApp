package com.example.upgradedaccountingapp.feature_view_cashflow_records.presentation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.upgradedaccountingapp.CashFlowMonitoringApp
import com.example.upgradedaccountingapp.common.di.ViewCashFlowRecordsPageUseCases
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.common.utils.setValueOnMainThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.upgradedaccountingapp.common.utils.Result

@HiltViewModel
class GetCashFlowRecordsViewModel @Inject constructor(
    private val useCases: ViewCashFlowRecordsPageUseCases
): AndroidViewModel(CashFlowMonitoringApp()) {
    //region whole list mode
    private val _wholeListModeState: MutableLiveData<WholeListModeState> = MutableLiveData(WholeListModeState.NoRecordsDisplayable)
    val wholeListModeState: LiveData<WholeListModeState> = _wholeListModeState

    private val _wholeListModeResult: MutableLiveData<WholeListModeResult> = MutableLiveData(WholeListModeResult.DismissDialog)
    val wholeListModeResult: LiveData<WholeListModeResult> = _wholeListModeResult

    var wholeCashFlowRecords: List<CashFlowRecord> = arrayListOf()

    fun processWholeListModeAction(action: WholeListModeAction) {
        when(action) {
            is WholeListModeAction.DeleteRecord -> deleteCashFlowRecord(action.cashFlowRecord)

            is WholeListModeAction.DismissDialog -> _wholeListModeResult.setValueOnMainThread(WholeListModeResult.DismissDialog)
        }
    }

    suspend fun getWholeListOfRecords() {
        when(val result = useCases.getCashFlowRecordsUseCase()){
            is Result.Success -> {
                result.data?.let {
                    if(result.data.isNotEmpty()) {
                        _wholeListModeState.setValueOnMainThread(WholeListModeState.AvailableRecordsToDisplay(it))
                        wholeCashFlowRecords = it
                    }
                    else
                        _wholeListModeState.setValueOnMainThread(WholeListModeState.NoRecordsDisplayable)
                } ?: _wholeListModeState.setValueOnMainThread(WholeListModeState.NoRecordsDisplayable)
            }

            else -> _wholeListModeState.setValueOnMainThread(WholeListModeState.NoRecordsDisplayable)
        }
    }

    fun deleteCashFlowRecord(cashFlowRecord: CashFlowRecord){
        viewModelScope.launch(IO) {
            when(useCases.deleteCashFlowRecordsUseCase(cashFlowRecord)) {
                is Result.Success -> { getWholeListOfRecords() }
                else -> _wholeListModeResult.setValueOnMainThread(WholeListModeResult.DeleteCashFlowRecordsFail)
            }
        }
    }
    //endregion

    //region monthly mode
    private val _monthlyModeState: MutableLiveData<MonthlyModeState> = MutableLiveData()
    val monthlyModeState: LiveData<MonthlyModeState> = _monthlyModeState

    private val _monthlyModeResult: MutableLiveData<MonthlyModeResult> = MutableLiveData()
    val monthlyModeResult: LiveData<MonthlyModeResult> = _monthlyModeResult

    fun processMonthlyModeAction(action: MonthlyModeAction) {
        when(action) {
            is MonthlyModeAction.PickMonthOfYear -> getCashFlowRecordsForMonthOfYear(action.month, action.year)
            is MonthlyModeAction.ViewDatePickerDialog -> _monthlyModeResult.setValueOnMainThread(MonthlyModeResult.SelectDate)
            is MonthlyModeAction.DismissDialog -> _monthlyModeResult.setValueOnMainThread(MonthlyModeResult.DismissDialog)
        }
    }

    fun getCashFlowRecordsForMonthOfYear(chosenMonth: Int, chosenYear: Int){
        viewModelScope.launch(IO) {
            val res = useCases.getCashFlowRecordsOfMonthUseCase(
                chosenMonth,
                chosenYear,
                wholeCashFlowRecords
            )
            when(res) {
                is Result.Success -> {
                    var fts = 0.0
                    var foodAndDrinks = 0.0
                    var drivingRelated = 0.0
                    var miscellaneous = 0.0

                    res.data.let { monthlyList ->
                        monthlyList?.forEach {
                            when(it.category) {
                                1 -> { fts += it.amount }
                                2 -> { drivingRelated += it.amount }
                                3 -> { foodAndDrinks += it.amount }
                                4 -> { miscellaneous += it.amount }
                            }
                        }
                        _monthlyModeState.setValueOnMainThread(MonthlyModeState.SuccessfulCalculation(fts, foodAndDrinks, drivingRelated, miscellaneous))
                    }
                }

                else -> {}
            }
        }
    }
    //endregion

    init {
        viewModelScope.launch(IO) { getWholeListOfRecords() }
    }
}

//region monthly mode state, action, result
sealed class MonthlyModeAction() {
    object ViewDatePickerDialog: MonthlyModeAction()
    data class PickMonthOfYear(val month: Int, val year: Int): MonthlyModeAction()
    object DismissDialog: MonthlyModeAction()
}

sealed class MonthlyModeResult() {
    object SelectDate: MonthlyModeResult()
    object DismissDialog: MonthlyModeResult()
}

sealed class MonthlyModeState() {
    data class SuccessfulCalculation(
        var fts: Double = 0.0,
        var foodAndDrinks: Double = 0.0,
        var drivingRelated: Double = 0.0,
        var miscellaneous: Double = 0.0,
    ): MonthlyModeState()
}
//endregion

//region whole list mode action, result, state
sealed class WholeListModeAction() {
    data class DeleteRecord(val cashFlowRecord: CashFlowRecord): WholeListModeAction()
    object DismissDialog: WholeListModeAction()
}

sealed class WholeListModeResult(){
    object DeleteCashFlowRecordsFail: WholeListModeResult()
    object DismissDialog: WholeListModeResult()
}

sealed class WholeListModeState() {
    object NoRecordsDisplayable: WholeListModeState()
    data class AvailableRecordsToDisplay(val records: List<CashFlowRecord>): WholeListModeState()
}
//endregion