package kr.rabbitmqscheduler.service

import kr.rabbitmqscheduler.entity.Post
import kr.rabbitmqscheduler.entity.PostType
import kr.rabbitmqscheduler.repository.PostRepository
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val rabbitTemplate: RabbitTemplate
) {

    fun register(content: String, publishedAt: OffsetDateTime?) {
        if(publishedAt == null) {
            postRepository.save(Post(content = content, postType = PostType.PUBLISHED))
            return
        }

        val post = postRepository.save(Post(content = content, postType = PostType.PENDING, publishedAt = publishedAt))

        val delayMillis = calculateDelayMillisFromOffsetDateTime(publishedAt)

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routing.key", post.id!!) {
            it.messageProperties.setHeader("x-delay", delayMillis)
            it
        }
        println("✅ 메시지 전송 완료: '${post.id!!}' (지연: ${delayMillis}ms)")
    }

    fun upload(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow {
            IllegalArgumentException("Post not found with id: $postId")
        }

        post.postType = PostType.PUBLISHED
    }

    fun delete(postId: Long) {
        val post = postRepository.findById(postId).orElseThrow {
            IllegalArgumentException("Post not found with id: $postId")
        }

        post.postType = PostType.DELETED
    }

    fun update(postId: Long, content: String, publishedAt: OffsetDateTime?) {
        val findPost = postRepository.findById(postId).orElseThrow {
            IllegalArgumentException("Post not found with id: $postId")
        }

        findPost.postType = PostType.DELETED

        val post = postRepository.save(Post(content = content, postType = PostType.PENDING, publishedAt = publishedAt!!))

        val delayMillis = calculateDelayMillisFromOffsetDateTime(publishedAt)

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routing.key", post.id!!) {
            it.messageProperties.setHeader("x-delay", delayMillis)
            it.messageProperties.deliveryMode = MessageDeliveryMode.PERSISTENT
            it
        }
    }

    @RabbitListener(queues = ["delayed.queue"])
    fun handle(message: String) {
        // 상태 확인
        val post = postRepository.findById(message.toLong()).orElseThrow {
            IllegalArgumentException("Post not found with id: $message")
        }

        if(post.postType == PostType.PENDING) {
            post.postType = PostType.PUBLISHED
        }

        println("⏰ 소비된 메시지: '$message' @ ${LocalDateTime.now()}")
    }

    fun calculateDelayMillisFromOffsetDateTime(scheduledTime: OffsetDateTime): Long {
        val now = OffsetDateTime.now(scheduledTime.offset) // 동일한 오프셋 기준
        val duration = Duration.between(now, scheduledTime)
        val delayMillis = duration.toMillis()

        if (delayMillis < 0) {
            throw IllegalArgumentException("예약 시간이 이미 지났습니다.")
        }

        return delayMillis
    }
}
