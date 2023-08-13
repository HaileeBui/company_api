package com.example.companyapi.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CompanyException :: class)
    fun companyExceptionHandler(exception: Exception): ResponseEntity<ApiError> {
        //val error= ApiError(exception.message)
        //return ResponseEntity(error, error.status)
        val status = if (exception.message?.contains("There is no company with id") == true) {
              HttpStatus.NOT_FOUND
        } else HttpStatus.BAD_REQUEST


        val error = ApiError(exception.message, status)
        return ResponseEntity(error, status)
    }
}