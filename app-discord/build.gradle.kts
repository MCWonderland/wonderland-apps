version = "1.1.0"

dependencies {
    implementation(project(":mojang"))
    implementation(project(":mongo"))
    implementation(project(":command"))

    implementation("net.dv8tion:JDA:5.0.0-alpha.22")
    implementation("com.google.inject:guice:5.1.0")
}



tasks.getByName("shadowJar") {
    dependsOn(":mojang:test")
    dependsOn(":mongo:test")
    dependsOn(":command:test")
    dependsOn(":test")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.mcwonderland.discord.ApplicationKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}


