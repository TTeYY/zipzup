import kotlinx.kover.api.CoverageEngine

java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.franzbecker.gradle-lombok")

    id("org.jmailen.kotlinter")
    id("org.jetbrains.kotlinx.kover")

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")
    kotlin("plugin.allopen")

    application
}

apply(from = rootProject.file("gradle/kotlin-on-groovy-tests.gradle"))

application {
    mainClass.set("com.tteyy.zipzup.ZipZupApplicationKt")
    mainClassName = "com.tteyy.zipzup.ZipZupApplicationKt" // for shadowJar
}

dependencies {
    val groovyVersion: String by project
    val spockVersion: String by project
    val jacksonVersion: String by project

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Log
    implementation("org.codehaus.janino:janino:3.0.6")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Spock
    testImplementation("org.codehaus.groovy:groovy-all:$groovyVersion")
    testImplementation("org.spockframework:spock-core:$spockVersion")
    testImplementation("org.spockframework:spock-spring:$spockVersion")
    testImplementation("com.github.tomakehurst:wiremock-standalone:2.27.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
            javaParameters = true
        }
    }

    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
            javaParameters = true
        }
    }

    kover {
        isEnabled = true
        coverageEngine.set(CoverageEngine.INTELLIJ)
        generateReportOnCheck.set(true)

        koverHtmlReport {
            isEnabled = true
        }

        koverXmlReport {
            isEnabled = true
        }
    }

    koverVerify {
        rule {
            name = "Minimal line coverage rate in percents"
            bound {
                minValue = 0  // TODO change this value to 80 when test code is ready
            }
        }
    }

    kotlinter {
        disabledRules = arrayOf("no-wildcard-imports")
    }

}
