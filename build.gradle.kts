import java.nio.charset.Charset

plugins {
}

val jvmEncoding: String = Charset.defaultCharset().name()
if (jvmEncoding != "UTF-8") {
    throw IllegalStateException("Build environment must be UTF-8 (it is: $jvmEncoding) - add '-Dfile.encoding=UTF-8' to the GRADLE_OPTS environment variable ")
}

if (!JavaVersion.current().isJava11Compatible) {
    println(JavaVersion.current())
    throw IllegalStateException("Must be built with Java 11 or higher")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "groovy")
    apply(plugin = "maven-publish")

    group = "com.tteyy.zipzup"
    version = rootProject.file("version.txt").readText(Charsets.UTF_8).trim()

    tasks.withType<JavaCompile> {
        options.encoding = "utf-8"
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    configurations {
        val retrofitVersion: String by project
        val jacksonVersion: String by project

        all {
            resolutionStrategy {
                force("com.squareup.retrofit2:retrofit:$retrofitVersion")
                force("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
            }
        }
    }

    val test: Test by tasks
    test.useJUnitPlatform()
}
