plugins {
    kotlin("kapt")
    id("java-test-fixtures")
}

dependencies {
    val kotlinWebfluxCommonVersion: String by project
    val groovyVersion: String by project
    val spockVersion: String by project

    testFixturesApi("org.codehaus.groovy:groovy-all:$groovyVersion")
    testFixturesApi("org.spockframework:spock-core:$spockVersion")
    testFixturesApi("org.spockframework:spock-spring:$spockVersion")
}
