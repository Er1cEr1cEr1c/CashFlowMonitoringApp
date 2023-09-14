package com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.repository_def

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord

interface GetCashFlowRecordsRepositoryDef {
    suspend fun getCashFlowRecords(): List<CashFlowRecord>
    suspend fun deleteCashFlowRecord(cashFlowRecord: CashFlowRecord)
}