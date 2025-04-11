package kr.taskscheduler.repository

import kr.taskscheduler.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
