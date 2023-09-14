package com.example.upgradedaccountingapp.feature_create_records.domain.use_case

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_create_records.domain.repository_def.InsertCashFlowRecordRepositoryDef
import com.example.upgradedaccountingapp.common.utils.Result

class InsertCashFlowRecord_UC(
    private val repository: InsertCashFlowRecordRepositoryDef
) {
    //for some reason, applying the try catch block here does nothing to stop a crash if bad values are entered
    suspend operator fun invoke(cashFlowRecord: CashFlowRecord): Result<Unit> {
        try {
            return Result.Success(repository.insertCashFlowRecord(cashFlowRecord))
        }
        catch (e: java.lang.Exception) {
            return Result.Failure("Insert Cash Flow Record Fail")
        }
    }
}