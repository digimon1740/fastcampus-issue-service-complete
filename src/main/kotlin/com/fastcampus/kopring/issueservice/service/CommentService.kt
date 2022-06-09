package com.fastcampus.kopring.issueservice.service

import com.fastcampus.kopring.issueservice.domain.Comment
import com.fastcampus.kopring.issueservice.domain.CommentRepository
import com.fastcampus.kopring.issueservice.model.CommentRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueService: IssueService,
) {

    @Transactional
    fun create(issueId: Long, userId: Long, request: CommentRequest): Comment {
        val issue = issueService.get(issueId)

        val comment = Comment(
            issue = issue,
            userId = userId,
            body = request.body,
        )

        issue.comments.add(comment)
        return commentRepository.save(comment)
    }

    @Transactional
    fun edit(id: Long, userId: Long, request: CommentRequest): Comment? =
        commentRepository.findByIdAndUserId(id, userId)?.run {
            body = request.body
            commentRepository.save(this)
        }

    fun delete(id: Long, userId: Long) = commentRepository.deleteByIdAndUserId(id, userId)


}
