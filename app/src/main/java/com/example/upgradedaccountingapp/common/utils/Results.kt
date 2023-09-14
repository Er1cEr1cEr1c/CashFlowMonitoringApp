package com.example.upgradedaccountingapp.common.utils

sealed class Result<out T> {
    companion object {
        const val ERROR_CODE_UNAUTHORIZED = 401
    }
    data class Success<T>(val data: T?) : Result<T>()
    data class Failure(val errors: String?, val errorCode: Int? = null, val rawException: Throwable? = null) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}
