package org.mcwonderland.domain.config

interface Config {
    val commandChannelId: String
    val commandPrefix: String
    val dbName: String
    val mongoConnection: String
    val settingsMongoId: String
    val commandLabels: CommandLabels
}

interface CommandLabels {
    val link: String
    val register: String

    val addToTeam: String
    val createTeam: String
    val removeTeam: String
    val listTeams: String
    val deleteTeam: String

    val listReg: String
    val toggleReg: String
}
