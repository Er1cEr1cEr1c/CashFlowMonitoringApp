package com.example.upgradedaccountingapp.feature_create_records.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord

@Dao
interface InsertCashFlowRecordDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCashFlowRecord(cashFlowRecord: CashFlowRecord)
}