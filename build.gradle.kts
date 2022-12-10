import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

version = "1.1-SNAPSHOT"


allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

subprojects {
    plugins.apply("org.jetbrains.kotlin.jvm")
    plugins.apply("com.github.johnrengelman.shadow")

    group = "org.mcwonderland"

    //use junit platform to run tests
    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    dependencies {
        if (project.name != "domain")
            implementation(project(":domain"))

        testImplementation(project(":mocks"))
        testImplementation(kotlin("test"))
        testImplementation("io.mockk:mockk:1.13.2")
    }
}