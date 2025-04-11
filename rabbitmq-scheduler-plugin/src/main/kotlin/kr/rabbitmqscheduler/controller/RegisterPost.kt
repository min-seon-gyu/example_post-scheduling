package kr.rabbitmqscheduler.controller

import java.time.OffsetDateTime

data class RegisterPost(
    val content: String,
    val publishedAt: OffsetDateTime?
)
