import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
}

group = "org.mcwonderland"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots")

}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-alpha.22")
    implementation("com.github.SparklingComet:java-mojang-api:-SNAPSHOT")
    implementation("org.mongodb:mongodb-driver-sync:4.7.0")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.5.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}