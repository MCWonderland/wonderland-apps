package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config

class ConfigStub : Config {
    override val commandChannelId: String = "commandChannelId"

    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnection: String = "mongodb://localhost:27017"
    override val settingsMongoId: String = "settingsId"

    override val commandLabels: CommandLabels = object : CommandLabels {
        override val addToTeam: String = "addtoteam"
        override val createTeam: String = "createTeam"
        override val link: String = "link"
        override val register: String = "register"
        override val removeTeam: String = "removeTeam"
        override val listTeams: String = "listTeams"
        override val listReg: String = "listReg"
        override val toggleReg: String = "toggleReg"
        override val deleteTeam: String = "deleteTeam"
    }

}