package com.example.companyapi.utils.exceptions

import org.springframework.http.HttpStatus

data class ApiError(
        private val _message: String?,
        val status: HttpStatus? = HttpStatus.BAD_REQUEST,
        val code: Int = status?.value() ?: HttpStatus.BAD_REQUEST.value()
) {
    val message: String
        get() = _message ?: "Something went wrong"
}
