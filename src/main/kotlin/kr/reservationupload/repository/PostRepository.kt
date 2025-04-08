package kr.reservationupload.repository

import kr.reservationupload.entity.Post
import kr.reservationupload.entity.PostType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE (:postType IS NULL OR p.postType = :postType)")
    fun gets(postType: PostType?): List<Post>
}
