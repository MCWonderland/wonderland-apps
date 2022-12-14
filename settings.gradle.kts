rootProject.name = "wonderland-apps"
include("domain")
include("mongo")
include("mojang")
include("command")
include("mocks")

include("app-discord")
include("app-web")

pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}
