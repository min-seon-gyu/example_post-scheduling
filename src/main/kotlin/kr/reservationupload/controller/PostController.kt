package kr.reservationupload.controller

import kr.reservationupload.entity.Post
import kr.reservationupload.entity.PostType
import kr.reservationupload.service.PostService
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/{postType}")
    fun gets(@PathVariable postType: PostType? = null): List<Post> {
        return postService.gets(postType)
    }

    @GetMapping("/{postId}")
    fun get(@PathVariable postId: Long): Post {
        return postService.get(postId)
    }


    @PostMapping
    fun register(@RequestBody registerPost: RegisterPost): Post {
        val post = postService.register(registerPost.content, registerPost.postType, registerPost.scheduledAt)

        return post
    }
}
