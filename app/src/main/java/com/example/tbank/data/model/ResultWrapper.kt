package com.example.tbank.data.model

import android.util.Log
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val message: String?): ResultWrapper<Nothing>()
    data class HttpError(val message: String?, val code: Int): ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Log.d("IOEXCP", throwable.toString())
                    ResultWrapper.Error("Проблемы с интернетом")
                }
                is HttpException -> {
                    Log.d("HTTPEXCP", throwable.toString())
                    ResultWrapper.HttpError(convertErrorBody(throwable), throwable.code())
                }
                else -> {
                    Log.d("neizv", throwable.message.toString())
                    throwable.stackTrace.forEach {
                        Log.d("neizv", it?.toString() ?: "")
                    }
                    ResultWrapper.Error("Неизвестная ошибка")
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(String::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}
