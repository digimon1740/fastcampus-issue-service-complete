package com.fastcampus.kopring.issueservice.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ServerException::class)
    fun handleException(ex: ServerException) = ErrorResponse(status = ex.status, message = ex.message)

}