package kr.taskscheduler.entity

import jakarta.persistence.*

@Entity
class Post(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column
    var postType: PostType,

    ) {

    fun upload() {
        require(this.postType == PostType.SCHEDULED)

        this.postType = PostType.PUBLISHED
    }
}
