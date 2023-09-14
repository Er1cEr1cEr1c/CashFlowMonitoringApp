package com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.repository_def.GetCashFlowRecordsRepositoryDef
import com.example.upgradedaccountingapp.common.utils.Result

class DeleteCashFlowRecordsUseCase(
    private val repository: GetCashFlowRecordsRepositoryDef
) {
    suspend operator fun invoke(cashFlowRecord: CashFlowRecord): Result<Unit> {
        try{
            return Result.Success(repository.deleteCashFlowRecord(cashFlowRecord))
        }
        catch (e:java.lang.Exception) {
            return Result.Failure("Delete Cash Flow Record fail")
        }
    }
}