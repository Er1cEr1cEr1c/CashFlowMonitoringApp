package com.example.upgradedaccountingapp.common.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

inline fun <reified T> MutableLiveData<T>.setValueOnMainThread(t: T?) {
    Handler(Looper.getMainLooper()).post {
        setValue(t)
    }
}

// Has to be called in a coroutine scope
suspend inline fun <reified T> MutableLiveData<T>.setValueOnMainContext(t: T?) {
    withContext(Dispatchers.Main) {
        value = t
    }
}