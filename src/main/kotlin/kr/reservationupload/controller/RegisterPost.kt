package kr.reservationupload.controller

import kr.reservationupload.entity.PostType
import java.time.OffsetDateTime

data class RegisterPost(
    val content: String,
    val postType: PostType,
    val scheduledAt: OffsetDateTime? = null
)
