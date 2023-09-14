package com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases

import android.util.Log
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.common.utils.Result
import com.example.upgradedaccountingapp.common.utils.calendarObject
import com.example.upgradedaccountingapp.common.utils.completeDateFormat
import java.util.*

class GetCashFlowRecordsOfMonthUseCase {
    suspend operator fun invoke(
        chosenMonth: Int,
        chosenYear: Int,
        cashFlowRecords: List<CashFlowRecord>
    ): Result<List<CashFlowRecord>> {
        try {
            setCalendarObjectToEarliesOrLatestOfMonth(chosenYear, chosenMonth, true)

            val minimumDateLong = calendarObject.timeInMillis
            Log.d("Ohoy Min: ", completeDateFormat.format(minimumDateLong) )

            setCalendarObjectToEarliesOrLatestOfMonth(chosenYear, chosenMonth, false)

            val maximumDateLong = calendarObject.timeInMillis
            Log.d("Ohoy Max: ", completeDateFormat.format(maximumDateLong))

            return Result.Success(
                cashFlowRecords.filter { minimumDateLong <= it.timestamp && it.timestamp <= maximumDateLong }
            )
        }
        catch(e:java.lang.Exception) {
            return Result.Failure("Get Cash Flow Records of month Fail")
        }
    }

    private fun setCalendarObjectToEarliesOrLatestOfMonth(
        chosenYear: Int,
        chosenMonth: Int,
        earliestOrNot: Boolean
    ) {
        calendarObject.set(Calendar.YEAR, chosenYear)
        if(earliestOrNot)
            calendarObject.set(Calendar.DAY_OF_MONTH, 1)
        else
            calendarObject.set(Calendar.DAY_OF_MONTH, calendarObject.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendarObject.set(Calendar.MONTH, chosenMonth)
        calendarObject.set(Calendar.HOUR_OF_DAY, 0);
        calendarObject.set(Calendar.MINUTE, 0);
        calendarObject.set(Calendar.SECOND, 0);
        calendarObject.set(Calendar.MILLISECOND, 0)
    }
}