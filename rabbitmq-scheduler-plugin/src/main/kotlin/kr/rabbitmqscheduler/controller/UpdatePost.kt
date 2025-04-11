package kr.rabbitmqscheduler.controller

import java.time.OffsetDateTime

class UpdatePost(
    val content: String,
    val publishedAt: OffsetDateTime?
)
