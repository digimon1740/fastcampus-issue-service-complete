package com.fastcampus.kopring.issueservice.model

import com.fastcampus.kopring.issueservice.domain.Comment
import com.fastcampus.kopring.issueservice.domain.Issue
import com.fastcampus.kopring.issueservice.domain.enums.IssuePriority
import com.fastcampus.kopring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kopring.issueservice.domain.enums.IssueType

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    val comments: List<CommentResponse> = emptyList(),
    val summary: String,
    val description: String,
    val userId: Long,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
) {
    companion object {
        operator fun invoke(issue: Issue) = IssueResponse(
            id = issue.id!!,
            comments = issue.comments.sortedByDescending(Comment::id).map(CommentResponse::of),
            summary = issue.summary,
            description = issue.description,
            userId = issue.userId,
            type = issue.type,
            priority = issue.priority,
            status = issue.status,
        )
    }
}
