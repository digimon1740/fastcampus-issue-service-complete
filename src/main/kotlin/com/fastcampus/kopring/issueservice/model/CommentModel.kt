package com.fastcampus.kopring.issueservice.model

import com.fastcampus.kopring.issueservice.domain.Comment

data class CommentRequest(
    val body: String,
)

data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val body: String,
    val username: String? = null,
) {

    companion object {

        operator fun invoke(comment: Comment) = with(comment) {
            CommentResponse(
                id = id!!,
                issueId = issue.id!!,
                userId = userId,
                body = body,
                username = username,
            )
        }
    }
}