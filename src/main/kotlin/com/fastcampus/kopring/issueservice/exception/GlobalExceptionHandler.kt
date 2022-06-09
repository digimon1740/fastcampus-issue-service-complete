package com.fastcampus.kopring.issueservice.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ServerException::class)
    fun handleServerException(ex: ServerException) = ErrorResponse(status = ex.status, message = ex.message)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception) = ErrorResponse(status = 500, message = "Internal Server Error")

}