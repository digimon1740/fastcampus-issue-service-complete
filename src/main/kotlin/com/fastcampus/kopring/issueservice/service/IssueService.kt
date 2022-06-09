package com.fastcampus.kopring.issueservice.service

import com.fastcampus.kopring.issueservice.domain.Comment
import com.fastcampus.kopring.issueservice.domain.CommentRepository
import com.fastcampus.kopring.issueservice.domain.Issue
import com.fastcampus.kopring.issueservice.domain.IssueRepository
import com.fastcampus.kopring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kopring.issueservice.exception.NotFoundException
import com.fastcampus.kopring.issueservice.model.CommentRequest
import com.fastcampus.kopring.issueservice.model.IssueRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
    private val commentRepository: CommentRepository,
) {

    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)

    fun get(id: Long) =
        issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")

    @Transactional
    fun create(userId: Long, request: IssueRequest): Issue {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return issueRepository.save(issue)
    }

    @Transactional
    fun edit(userId: Long, issueId: Long, request: IssueRequest) =
        with(get(issueId)) {
            this.summary = request.summary
            this.description = request.description
            this.userId = userId
            this.type = request.type
            this.priority = request.priority
            this.status = request.status
            issueRepository.save(this)
        }

    fun delete(id: Long) = issueRepository.deleteById(id)


    @Transactional
    fun createComment(issueId: Long, userId: Long, request: CommentRequest): Issue {
        val issue = get(issueId)

        val comment = Comment(
            issue = issue,
            userId = userId,
            body = request.body,
        )

        issue.comments.add(comment)
        commentRepository.save(comment)
        return issue
    }

    @Transactional
    fun editComment(id: Long, userId: Long, request: CommentRequest): Comment? =
        commentRepository.findByIdAndUserId(id, userId)?.run {
            body = request.body
            commentRepository.save(this)
        }

    fun delete(id: Long, userId: Long) = commentRepository.deleteByIdAndUserId(id, userId)


}
