package com.example.upgradedaccountingapp.feature_create_records.data.repository_impl

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_create_records.data.InsertCashFlowRecordDao
import com.example.upgradedaccountingapp.feature_create_records.domain.repository_def.InsertCashFlowRecordRepositoryDef

class InsertCashFlowRecordRepositoryImpl(
    private val dao: InsertCashFlowRecordDao
) : InsertCashFlowRecordRepositoryDef {

    override suspend fun insertCashFlowRecord(cashFlowRecord: CashFlowRecord) {
        dao.insertCashFlowRecord(cashFlowRecord)
    }

}