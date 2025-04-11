package kr.rabbitmqscheduler.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun delayedExchange(): CustomExchange {
        val args = mapOf("x-delayed-type" to "direct")
        return CustomExchange(
            "delayed.exchange", // Exchange 이름(식별자)
            "x-delayed-message", // Exchange 타입 지정(Delayed Message Plugin 전용 타입)
            true, // 서버 재시작 후에도 Exchange를 유지할지 여부
            false, // Exchange가 사용되지 않을 경우 자동 삭제할지 여부
            args
        )
    }

    @Bean
    fun delayedQueue(): Queue = Queue("delayed.queue", true)

    @Bean
    fun binding(
        delayedQueue: Queue,
        delayedExchange: CustomExchange
    ): Binding = BindingBuilder
        .bind(delayedQueue)
        .to(delayedExchange)
        .with("delayed.routing.key")
        .noargs()
}
