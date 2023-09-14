package com.example.upgradedaccountingapp.feature_view_cashflow_records.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord

@Dao
interface ViewCashFlowRecordsDao {
    @Query("SELECT * FROM CashFlowRecord ORDER BY id DESC")
    fun getCashFlowRecord() : MutableList<CashFlowRecord>

    @Delete
    fun deleteCashFlowRecord(cashFlowRecord: CashFlowRecord)
}