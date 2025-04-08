package kr.reservationupload.entity

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

    val scheduledAt: OffsetDateTime? = null

) {

    fun upload() {
        require(this.postType == PostType.SCHEDULED)

        this.postType = PostType.PUBLISHED
    }
}
