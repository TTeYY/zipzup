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
    api(project(":zipzup-port-persistence"))

    // Spring
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation(
        group = "io.netty",
        name = "netty-resolver-dns-native-macos",
        version = "4.1.72.Final",
        classifier = "osx-aarch_64"
    ) // for M1 Macbook

    // Test
    testImplementation(testFixtures(project(":zipzup-common")))
    testImplementation("com.github.tomakehurst:wiremock-standalone:2.27.2")
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
