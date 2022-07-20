import kotlinx.kover.api.CoverageEngine

plugins {
    kotlin("kapt")
    id("org.jetbrains.kotlinx.kover")
}

apply(from = rootProject.file("gradle/kotlin-on-groovy-tests.gradle"))

dependencies {

    implementation(project(":zipzup-common"))
    implementation(project(":zipzup-domain"))
    implementation(project(":zipzup-port-web"))
    implementation(project(":zipzup-port-persistence"))

    // Test
    testImplementation(testFixtures(project(":zipzup-common")))
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
