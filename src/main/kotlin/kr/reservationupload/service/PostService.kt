package kr.reservationupload.service

import kr.reservationupload.entity.Post
import kr.reservationupload.entity.PostType
import kr.reservationupload.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {

    fun gets(postType: PostType?): List<Post> {
        return postRepository.gets(postType)
    }

    fun get(id: Long): Post {
        return postRepository.findById(id).orElseThrow { IllegalArgumentException("Post not found") }
    }

    fun register(content: String, postType: PostType, scheduledAt: OffsetDateTime?): Post {
        return postRepository.save(Post(content = content, postType = postType, scheduledAt = scheduledAt))
    }

    fun upload(id: Long): Post {
        val post = postRepository.findById(id).orElseThrow { IllegalArgumentException("Post not found") }

        post.upload()

        return post
    }
}
