package com.fastcampus.kopring.issueservice.web

import com.fastcampus.kopring.issueservice.config.AuthUser
import com.fastcampus.kopring.issueservice.model.CommentRequest
import com.fastcampus.kopring.issueservice.model.CommentResponse
import com.fastcampus.kopring.issueservice.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/api/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ) = commentService.create(issueId, authUser.userId, request).let(CommentResponse::of)

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: CommentRequest,
    ) = commentService.edit(id, authUser.userId, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable id: Long
    ) {
        commentService.delete(id, authUser.userId)
    }

}