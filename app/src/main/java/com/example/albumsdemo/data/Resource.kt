package com.example.albumsdemo.data

sealed class Resource<out T>(
    val data: T? = null,
    val code: Int? = null,
    val throwable: Throwable? = null,
    val errorResponse: ErrorResponse? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class HttpError(code: Int? = null, throwable: Throwable? = null, errorResponse: ErrorResponse? = null): Resource<Nothing>(null, code, throwable, errorResponse)
    class OtherError(throwable: Throwable? = null): Resource<Nothing>(null, null, throwable)
}