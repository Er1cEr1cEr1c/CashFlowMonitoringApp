package com.example.upgradedaccountingapp.common.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CashFlowRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val incomeOrNot: Boolean,
    val category: Int,
    val amount: Double,
    val comment: String
)
