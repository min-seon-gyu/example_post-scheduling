package kr.taskscheduler.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class TaskSchedulerConfig {

    @Bean
    fun taskScheduler(): ThreadPoolTaskScheduler {
        val scheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = 10  // 필요에 따라 스레드 풀 사이즈 조정
        scheduler.setThreadNamePrefix("PostScheduler-")
        return scheduler
    }
}
