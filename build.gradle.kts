import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("kotlin-allopen")
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.Embeddable")
        annotation("javax.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
    }

    group = "reservation-upload"
    version = "0.0.1-SNAPSHOT"

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        runtimeOnly("com.mysql:mysql-connector-j")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":task-scheduler") {

    tasks.withType<BootJar> {
        archiveFileName.set("task-scheduler.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
        archiveFileName.set("task-scheduler.zip")

        from("build/libs/task-scheduler.jar") { into("") }
        from("../.platform") { into(".platform") }
        from("../procfiles/api/Procfile") { into("") }
    }
}

project(":rabbitmq-scheduler-plugin") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-amqp")
    }

    tasks.withType<BootJar> {
        archiveFileName.set("rabbitmq-scheduler.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
        archiveFileName.set("rabbitmq-scheduler.zip")

        from("build/libs/rabbitmq-scheduler.jar") { into("") }
        from("../.platform") { into(".platform") }
        from("../procfiles/api/Procfile") { into("") }
    }
}
