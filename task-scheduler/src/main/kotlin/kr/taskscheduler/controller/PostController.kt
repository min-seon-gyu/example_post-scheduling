package kr.taskscheduler.controller

import kr.taskscheduler.service.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun register(@RequestBody registerPost: RegisterPost) {
        postService.register(registerPost.content, registerPost.postType, registerPost.timestamp)
    }
}
