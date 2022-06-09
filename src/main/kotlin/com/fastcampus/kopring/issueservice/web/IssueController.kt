package com.fastcampus.kopring.issueservice.web

import com.fastcampus.kopring.issueservice.config.AuthUser
import com.fastcampus.kopring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kopring.issueservice.model.IssueRequest
import com.fastcampus.kopring.issueservice.model.IssueResponse
import com.fastcampus.kopring.issueservice.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/api/issues")
class IssueController(
    private val issueService: IssueService,
) {

    @GetMapping
    fun getAll(
        authUser: AuthUser,
        @RequestParam status: IssueStatus
    ) = issueService.getAll(status)?.map { IssueResponse(it) }

    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest
    ) = issueService.create(authUser.userId, request).let(IssueResponse::invoke)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) =
        issueService.get(id).let(IssueResponse::invoke)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: IssueRequest
    ) = issueService.edit(authUser.userId, id, request).let(IssueResponse::invoke)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        issueService.delete(id)
    }


}