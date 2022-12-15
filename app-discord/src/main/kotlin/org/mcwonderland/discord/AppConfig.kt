package org.mcwonderland.discord

import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.CommandLabelsDefault
import org.mcwonderland.discord.config.Config
import java.util.*

class AppConfig(private val properties: Properties) : Config {
    override val commandPrefix: String = properties.getProperty("command-prefix")
    override val dbName: String = properties.getProperty("db-name")
    override val mongoConnectionEnv: String = properties.getProperty("mongo-connection")

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}