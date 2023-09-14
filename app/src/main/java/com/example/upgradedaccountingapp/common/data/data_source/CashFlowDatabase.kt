package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_create_records.data.InsertCashFlowRecordDao
import com.example.upgradedaccountingapp.feature_view_cashflow_records.data.ViewCashFlowRecordsDao

@Database(entities = [CashFlowRecord::class], version = 1)
abstract class CashFlowDatabase: RoomDatabase() {

    abstract val insertCashFlowRecordDao: InsertCashFlowRecordDao
    abstract val viewCashFlowRecordsDao: ViewCashFlowRecordsDao

    companion object {
        const val DATABASE_NAME = "cash_flow_db"
    }
}