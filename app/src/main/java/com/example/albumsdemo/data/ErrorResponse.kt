package com.example.albumsdemo.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/*
*  This error response should be generic enough for all API error.
* */
@JsonClass(generateAdapter = true)
class ErrorResponse(
    @Json(name = "status") val status: String = "",
    @Json(name = "error") val error: String = "",
    @Json(name = "message") val message: String = "",
    @Json(name = "remainingTime") val remainingTime: Int = 0 // for OTP edge case
){
    companion object{
        fun getEmptyErrorResponse(): ErrorResponse{
            return ErrorResponse("", "", "", 0)
        }
    }

}