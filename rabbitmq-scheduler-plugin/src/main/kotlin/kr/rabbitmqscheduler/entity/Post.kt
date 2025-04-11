package kr.rabbitmqscheduler.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
class Post(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column
    var postType: PostType,

    @Column
    var publishedAt: OffsetDateTime = OffsetDateTime.now()

    ) {

    fun upload() {
        require(this.postType == PostType.PENDING)

        this.postType = PostType.PUBLISHED
    }
}
