package com.example.upgradedaccountingapp.common.di

import android.app.Application
import androidx.room.Room
import com.example.upgradedaccountingapp.feature_create_records.data.repository_impl.InsertCashFlowRecordRepositoryImpl
import com.example.upgradedaccountingapp.feature_create_records.domain.repository_def.InsertCashFlowRecordRepositoryDef
import com.example.upgradedaccountingapp.feature_create_records.domain.use_case.InsertCashFlowRecord_UC
import com.example.upgradedaccountingapp.feature_view_cashflow_records.data.repository_impl.GetCashFlowRecordsRepositoryImpl
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.repository_def.GetCashFlowRecordsRepositoryDef
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases.DeleteCashFlowRecordsUseCase
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases.GetCashFlowRecordsOfMonthUseCase
import com.example.upgradedaccountingapp.feature_view_cashflow_records.domain.use_cases.GetCashFlowRecordsUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.CashFlowDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCashFlowDatabase(app: Application): CashFlowDatabase {
        return Room.databaseBuilder(
            app,
            CashFlowDatabase::class.java,
            CashFlowDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideInsertCashFlowRepository(db: CashFlowDatabase): InsertCashFlowRecordRepositoryDef {
        return InsertCashFlowRecordRepositoryImpl(db.insertCashFlowRecordDao)
    }

    @Provides
    @Singleton
    fun provideInsertCashFlowRecordUseCases(repository: InsertCashFlowRecordRepositoryDef): InsertCashFlowRecord_UC {
        return InsertCashFlowRecord_UC(repository)
    }

    @Provides
    @Singleton
    fun provideViewCashFlowRecordsPageRepository(db: CashFlowDatabase): GetCashFlowRecordsRepositoryDef {
        return GetCashFlowRecordsRepositoryImpl(db.viewCashFlowRecordsDao)
    }

    @Provides
    @Singleton
    fun provideViewCashFlowRecordsPageUseCases(repository: GetCashFlowRecordsRepositoryDef): ViewCashFlowRecordsPageUseCases {
        return ViewCashFlowRecordsPageUseCases(
            getCashFlowRecordsUseCase = GetCashFlowRecordsUseCase(repository),
            getCashFlowRecordsOfMonthUseCase = GetCashFlowRecordsOfMonthUseCase(),
            deleteCashFlowRecordsUseCase = DeleteCashFlowRecordsUseCase(repository)
        )
    }
}

data class ViewCashFlowRecordsPageUseCases(
    val getCashFlowRecordsUseCase: GetCashFlowRecordsUseCase,
    val getCashFlowRecordsOfMonthUseCase: GetCashFlowRecordsOfMonthUseCase,
    val deleteCashFlowRecordsUseCase: DeleteCashFlowRecordsUseCase
)