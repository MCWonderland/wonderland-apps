package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config

class ConfigStub : Config {

    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnection: String = "mongodb://localhost:27017"

    override val commandLabels: CommandLabels = object : CommandLabels {
        override val createTeam: String = "createTeam"
        override val link: String = "link"
        override val register: String = "register"
        override val removeTeam: String = "removeTeam"
        override val listTeams: String = "listTeams"
    }

}