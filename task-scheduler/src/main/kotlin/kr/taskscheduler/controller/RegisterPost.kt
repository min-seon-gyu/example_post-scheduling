package kr.taskscheduler.controller

import kr.taskscheduler.entity.PostType
import java.time.OffsetDateTime

data class RegisterPost(
    val content: String,
    val postType: PostType,
    val timestamp: OffsetDateTime?
)
