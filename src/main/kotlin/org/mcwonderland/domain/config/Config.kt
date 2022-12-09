package org.mcwonderland.domain.config

interface Config {
    val commandPrefix: String
    val dbName: String
    val mongoConnection: String
    val commandLabels: CommandLabels
}

interface CommandLabels {
    val addToTeam: String
    val createTeam: String
    val link: String
    val register: String
    val removeTeam: String
    val listTeams: String
    val listReg: String
    val deleteTeam: String
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
    override val deleteTeam: String = "deleteTeam"
    override val clearReg: String = "clearreg"
}