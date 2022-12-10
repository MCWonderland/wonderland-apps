version = "1.0.0"


dependencies {
    implementation(project(":mojang"))
    implementation(project(":mongo"))

    implementation("net.dv8tion:JDA:5.0.0-alpha.22")
    implementation("com.google.inject:guice:5.1.0")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.mcwonderland.ApplicationKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}


