rootProject.name = "zipzup"

pluginManagement {
    val springBootVersion: String by settings
    val kotlinVersion: String by settings
    val kotlinterVersion: String by settings
    val koverVersion: String by settings
    val detektVersion: String by settings

    plugins {
        id("java")
        id("groovy")

        id("org.springframework.boot") version springBootVersion
        id("com.github.ben-manes.versions") version "0.39.0"
        id("com.github.johnrengelman.shadow") version "7.1.0"
        id("io.spring.dependency-management") version "1.0.11.RELEASE"
        id("io.franzbecker.gradle-lombok") version "5.0.0"

        id("org.jmailen.kotlinter") version kotlinterVersion
        id("org.jetbrains.kotlinx.kover") version koverVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion

        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

include("zipzup-common")

include("zipzup-domain")
include("zipzup-port-web")
include("zipzup-port-persistence")
include("zipzup-application")
include("zipzup-adapter-web")
include("zipzup-adapter-persistence")
include("zipzup-web")

rootProject.children.forEach { project ->
    project.buildFileName = "${project.name.toLowerCase()}.gradle.kts"
    assert(project.buildFile.isFile)
}
