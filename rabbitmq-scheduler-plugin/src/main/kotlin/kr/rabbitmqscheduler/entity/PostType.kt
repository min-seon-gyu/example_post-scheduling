package kr.rabbitmqscheduler.entity

enum class PostType {
    PUBLISHED, // 등록됨
    PENDING, // 예약됨
    DELETED // 삭제됨
}
