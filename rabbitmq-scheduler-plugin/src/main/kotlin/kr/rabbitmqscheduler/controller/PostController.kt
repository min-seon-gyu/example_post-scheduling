package kr.rabbitmqscheduler.controller

import kr.rabbitmqscheduler.service.PostService
import org.springframework.web.bind.annotation.PathVariable
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
        postService.register(registerPost.content, registerPost.publishedAt)
    }

    @PostMapping("/upload/{postId}")
    fun upload(@PathVariable postId: Long) {
        postService.upload(postId)
    }

    @PostMapping("/delete/{postId}")
    fun delete(@PathVariable postId: Long) {
        postService.delete(postId)
    }

    @PostMapping("/update/{postId}")
    fun update(@PathVariable postId: Long, @RequestBody updatePost: UpdatePost) {
        postService.update(postId, updatePost.content, updatePost.publishedAt)
    }
}
