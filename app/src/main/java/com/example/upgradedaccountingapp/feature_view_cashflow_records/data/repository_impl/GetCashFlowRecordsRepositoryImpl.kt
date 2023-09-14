package com.example.upgradedaccountingapp.feature_view_cashflow_records.data.repository_impl

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_view_cashflow_records.data.ViewCashFlowRecordsDao
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.repository_def.GetCashFlowRecordsRepositoryDef

class GetCashFlowRecordsRepositoryImpl(
    private val dao: ViewCashFlowRecordsDao
): GetCashFlowRecordsRepositoryDef {
    override suspend fun getCashFlowRecords(): List<CashFlowRecord> {
        return dao.getCashFlowRecord()
    }

    override suspend fun deleteCashFlowRecord(cashFlowRecord: CashFlowRecord) {
        return dao.deleteCashFlowRecord(cashFlowRecord)
    }
}