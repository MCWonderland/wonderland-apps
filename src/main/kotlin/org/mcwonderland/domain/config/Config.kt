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
    val register: String
    val removeTeam: String
    val listTeams: String
    val listReg: String
}
