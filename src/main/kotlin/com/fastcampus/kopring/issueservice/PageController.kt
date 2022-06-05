package com.fastcampus.kopring.issueservice

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping
    fun index() = "index"

    @GetMapping("/issueapp")
    fun issueApp() = "issueapp"

    @GetMapping("signup")
    fun signUp() = "signup"
}