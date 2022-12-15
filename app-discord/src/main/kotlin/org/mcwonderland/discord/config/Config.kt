package org.mcwonderland.discord.config

interface Config {
    val commandPrefix: String
    val dbName: String
    val mongoConnectionEnv: String
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
    val clearReg: String
}


class CommandLabelsDefault : CommandLabels {
    override val addToTeam: String = "addtoteam"
    override val createTeam: String = "createTeam"
    override val link: String = "link"
    override val register: String = "register"
    override val removeTeam: String = "removeTeam"
    override val listTeams: String = "listTeams"
    override val listReg: String = "listreg"
    override val toggleReg: String = "togglereg"
    override val deleteTeam: String = "deleteTeam"
    override val clearReg: String = "clearreg"
}