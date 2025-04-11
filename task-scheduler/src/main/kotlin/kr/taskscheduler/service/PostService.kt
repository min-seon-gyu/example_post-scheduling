package kr.taskscheduler.service

import kr.taskscheduler.entity.Post
import kr.taskscheduler.entity.PostType
import kr.taskscheduler.repository.PostRepository
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val taskScheduler: TaskScheduler
) {

    fun register(content: String, postType: PostType, timestamp: OffsetDateTime?) {
        when (postType) {
            PostType.PUBLISHED -> postRepository.save(Post(content = content, postType = postType))
            PostType.SCHEDULED -> {
                taskScheduler.schedule(
                    {
                        println("Scheduled task executed")
                        postRepository.save(Post(content = content, postType = postType))
                    },
                    timestamp!!.toInstant()
                )
            }
        }
    }
}
