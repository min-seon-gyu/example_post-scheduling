package kr.rabbitmqscheduler.repository

import kr.rabbitmqscheduler.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
