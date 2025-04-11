package kr.rabbitmqscheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitmqSchedulerApplication

fun main(args: Array<String>) {
    runApplication<RabbitmqSchedulerApplication>(*args)
}
