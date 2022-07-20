import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.charset.Charset

plugins {
    kotlin("jvm")
    id("org.jmailen.kotlinter")
    id("io.gitlab.arturbosch.detekt")
}

val jvmEncoding: String = Charset.defaultCharset().name()
if (jvmEncoding != "UTF-8") {
    throw IllegalStateException("Build environment must be UTF-8 (it is: $jvmEncoding) - add '-Dfile.encoding=UTF-8' to the GRADLE_OPTS environment variable ")
}

if (!JavaVersion.current().isJava11Compatible) {
    println(JavaVersion.current())
    throw IllegalStateException("Must be built with Java 11 or higher")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "groovy")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "utf-8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
            javaParameters = true
        }
    }
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jmailen.kotlinter")

    group = "com.tteyy.zipzup"
    version = rootProject.file("version.txt").readText(Charsets.UTF_8).trim()

    kotlinter {
        disabledRules = arrayOf("import-ordering")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.register<Detekt>("detektAll") {
    description = "Detekt build for all modules"

    parallel = true
    ignoreFailures = false
    buildUponDefaultConfig = true
    allRules = false

    setSource(file(projectDir))
    config.setFrom(files(rootProject.file("gradle/detekt/detekt-config.yml").path))

    include("**/*.kt")
    exclude("**/resources/**", "**/build/**")

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
    }
}
