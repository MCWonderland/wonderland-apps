package org.mcwonderland.domain.config

interface Config {

    val commandPrefix: String
    val dbName: String
    val mongoConnection: String
    val commandLabels: CommandLabels
}

interface CommandLabels {
    val createTeam: String
    val link: String
}
