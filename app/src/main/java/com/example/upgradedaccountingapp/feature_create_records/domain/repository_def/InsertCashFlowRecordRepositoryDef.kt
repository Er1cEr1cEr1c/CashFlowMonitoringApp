package com.example.upgradedaccountingapp.feature_create_records.domain.repository_def

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord

interface InsertCashFlowRecordRepositoryDef {
    suspend fun insertCashFlowRecord(cashFlowRecord: CashFlowRecord)
}