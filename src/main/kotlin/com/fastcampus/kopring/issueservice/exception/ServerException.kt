package com.fastcampus.kopring.issueservice.exception

sealed class ServerException(
    val status: Int,
    override val message: String,
) : RuntimeException(message)

data class NotFoundException(
    override val message: String
) : ServerException(404, message)



