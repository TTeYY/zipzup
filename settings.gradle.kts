rootProject.name = "zipzup"

pluginManagement {
    val springBootVersion: String by settings
    val kotlinVersion: String by settings

    plugins {
        id("java")
        id("groovy")

        id("org.springframework.boot") version springBootVersion
        id("com.github.ben-manes.versions") version "0.39.0"
        id("com.github.johnrengelman.shadow") version "7.1.0"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        id("io.franzbecker.gradle-lombok") version "5.0.0"

        id("pmd")
        id("checkstyle")
        id("com.github.spotbugs") version "4.7.1"
        id("org.jmailen.kotlinter") version "3.7.0"
        id("org.jetbrains.kotlinx.kover") version "0.4.4"

        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

include("zipzup-web")

rootProject.children.forEach { project ->
    project.buildFileName = "${project.name.toLowerCase()}.gradle.kts"
    assert(project.buildFile.isFile)
}
