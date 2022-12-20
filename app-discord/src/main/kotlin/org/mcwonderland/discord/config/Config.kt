package org.mcwonderland.discord.config

interface Config {
    val botToken: String
    val commandPrefix: String
    val dbName: String
    val mongoUrl: String
    val commandLabels: CommandLabels
}

interface CommandLabels {
    val removeReg: String
    val link: String
    val register: String

    val addToTeam: String
    val createTeam: String
    val removeTeam: String
    val listTeams: String
    val deleteTeam: String

    val listReg: String
    val toggleReg: String
    val clearReg: String
}


class CommandLabelsDefault : CommandLabels {
    override val addToTeam: String = "addtoteam"
    override val createTeam: String = "createteam"
    override val removeReg: String = "removereg"
    override val link: String = "link"
    override val register: String = "register"
    override val removeTeam: String = "removeteam"
    override val listTeams: String = "listteams"
    override val listReg: String = "listreg"
    override val toggleReg: String = "togglereg"
    override val deleteTeam: String = "deleteteam"
    override val clearReg: String = "clearreg"
}