package com.fastcampus.kopring.issueservice.dto

import com.fastcampus.kopring.issueservice.domain.Comment

data class CommentRequest(
    val body: String,
)

data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val body: String,
    // UserService 연동 대비
    val username: String? = null,
    val profileUrl: String? = null,
) {

    companion object {

        fun of(comment: Comment) = with(comment) {
            CommentResponse(
                id = id!!,
                issueId = issue.id!!,
                userId = userId,
                body = body,
            )
        }

    }
}