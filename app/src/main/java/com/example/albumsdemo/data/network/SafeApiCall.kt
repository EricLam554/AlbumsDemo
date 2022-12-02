package com.example.albumsdemo.data.network

import com.example.albumsdemo.data.ErrorResponse
import com.example.albumsdemo.data.Resource
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object SafeApiCall {
    suspend operator fun <T> invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                val result = apiCall.invoke()
                if (result != null) {
                    Resource.Success(result as T)
                } else {
                    Resource.OtherError(NetWorkThrowable.BodyParsing.throwable)
                }

            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.OtherError(throwable)
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = getErrorResponse(throwable)?: ErrorResponse.getEmptyErrorResponse()
                        Resource.HttpError(code, NetworkErrorHelper.getThrowable(code).throwable, errorResponse)
                    }
                    is JsonDataException -> Resource.OtherError(NetWorkThrowable.JSONParsing.throwable)
                    else -> {
                        Resource.OtherError(throwable)
                    }
                }
            }
        }
    }

    private fun getErrorResponse(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

    enum class NetWorkThrowable(val throwable: Throwable) {
        InformationalError(Throwable("[Http] Informational error, code 1XX")),
        RedirectionError(Throwable("[Http] Redirection error, code 3XX")),
        OtherClientError(Throwable("[Http] Other client error, code 4XX")),
        OtherServerError(Throwable("[Http] Other server error, code 5XX")),
        NotModified(Throwable("[Http] Not modified, code 304")),
        BadRequest(Throwable("[Http] Bad request, code 400")),
        Unauthorized(Throwable("[Http] Unauthorized, code 401")),
        Forbidden(Throwable("[Http] Forbidden, code 403")),
        NotFound(Throwable("[Http] Not found, code 404")),
        InternalServerError(Throwable("[Http] Internal server error, code 500")),
        ServiceUnavailable(Throwable("[Http] Service unavailable, code 503")),
        GatewayTimeout(Throwable("[Http] Gateway timeout, code 504")),
        Undefined(Throwable("[Http] Undefined, code 0")),
        BodyParsing(Throwable("[Parsing] HTTP Status in Successful, but failed to parse the response")),
        JSONParsing(Throwable("[JsonSyntaxException] failed to parse the JOSN response")),


    }

    object NetworkErrorHelper {
        //300
        private const val NOT_MODIFIED = 304

        //400
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404

        //500
        private const val INTERNAL_SERVER_ERROR = 500
        private const val SERVICE_UNAVAILABLE = 503
        private const val GATEWAY_TIMEOUT = 504


        fun getThrowable(errorCode: Int): NetWorkThrowable {
            when (errorCode) {
                NOT_MODIFIED -> return NetWorkThrowable.NotModified
                BAD_REQUEST -> return NetWorkThrowable.BadRequest
                UNAUTHORIZED -> return NetWorkThrowable.Unauthorized
                FORBIDDEN -> return NetWorkThrowable.Forbidden
                NOT_FOUND -> return NetWorkThrowable.NotFound
                INTERNAL_SERVER_ERROR -> return NetWorkThrowable.InternalServerError
                SERVICE_UNAVAILABLE -> return NetWorkThrowable.ServiceUnavailable
                GATEWAY_TIMEOUT -> return NetWorkThrowable.GatewayTimeout
            

                in 100..199 -> return NetWorkThrowable.InformationalError
                in 300..399 -> return NetWorkThrowable.RedirectionError
                in 400..499 -> return NetWorkThrowable.OtherClientError
                in 500..600 -> return NetWorkThrowable.OtherServerError


                else -> {
                    return NetWorkThrowable.Undefined
                }
            }
        }
    }
}
