plugins {
    kotlin("kapt")
}

dependencies {
    val jacksonVersion: String by project

    implementation(project(":zipzup-common"))

    // Log
    api("org.slf4j:slf4j-api:1.7.36")
    api("ch.qos.logback:logback-classic:1.2.11")
    api("org.codehaus.janino:janino:3.0.6")

    // Jackson
    api("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    api("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    api("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
}
