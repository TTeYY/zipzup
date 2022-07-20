import kotlinx.kover.api.CoverageEngine

plugins {
    kotlin("kapt")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlinx.kover")
}

apply(from = rootProject.file("gradle/kotlin-on-groovy-tests.gradle"))

dependencies {

    implementation(project(":zipzup-common"))
    api(project(":zipzup-port-web"))

    // Spring
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Test
    testImplementation(testFixtures(project(":zipzup-common")))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}

tasks {
    kover {
        isDisabled = false
        coverageEngine.set(CoverageEngine.INTELLIJ)
        generateReportOnCheck = true

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
                minValue = 0
            }
        }
    }
}
