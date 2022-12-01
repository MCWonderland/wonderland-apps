package org.mcwonderland

import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.Config
import java.util.*

class AppConfig(private val properties: Properties) : Config {
    override val commandChannelId: String = properties.getProperty("command-channel-id")
    override val commandPrefix: String = properties.getProperty("command-prefix")
    override val dbName: String = properties.getProperty("db-name")
    override val mongoConnection: String = properties.getProperty("mongo-connection")

    override val commandLabels: CommandLabels = object : CommandLabels {
        override val createTeam: String = properties.getProperty("command.create-team")
        override val link: String = properties.getProperty("command.link")
        override val register: String = properties.getProperty("command.register")
        override val removeTeam: String = properties.getProperty("command.remove-team")
        override val listTeams: String = properties.getProperty("command.list-teams")
        override val listReg: String = properties.getProperty("command.list-reg")
    }
}