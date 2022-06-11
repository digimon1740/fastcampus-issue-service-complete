package com.fastcampus.kopring.issueservice.service

import com.fastcampus.kopring.issueservice.domain.Issue
import com.fastcampus.kopring.issueservice.domain.IssueRepository
import com.fastcampus.kopring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kopring.issueservice.exception.NotFoundException
import com.fastcampus.kopring.issueservice.model.IssueRequest
import com.fastcampus.kopring.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {

    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)?.map { IssueResponse(it) }

    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        return IssueResponse(issue)
    }

    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return IssueResponse(issueRepository.save(issue))
    }

    @Transactional
    fun edit(userId: Long, issueId: Long, request: IssueRequest): IssueResponse {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("이슈가 존재하지 않습니다")
        return with(issue) {
            this.summary = request.summary
            this.description = request.description
            this.userId = userId
            this.type = request.type
            this.priority = request.priority
            this.status = request.status
            IssueResponse(issueRepository.save(this))
        }
    }

    fun delete(id: Long) = issueRepository.deleteById(id)
}
