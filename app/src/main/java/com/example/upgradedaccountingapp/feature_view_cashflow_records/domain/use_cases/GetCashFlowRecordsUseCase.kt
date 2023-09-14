package com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases

import com.example.upgradedaccountingapp.common.domain.model.CashFlowRecord
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.repository_def.GetCashFlowRecordsRepositoryDef
import com.example.upgradedaccountingapp.common.utils.Result

class GetCashFlowRecordsUseCase(
    private val repository: GetCashFlowRecordsRepositoryDef
) {
    suspend operator fun invoke(): Result<List<CashFlowRecord>> {
        try {
            return Result.Success(repository.getCashFlowRecords())
        }
        catch(e:java.lang.Exception) {
            return Result.Failure("Get Cash Flow Records Fail")
        }
    }
}