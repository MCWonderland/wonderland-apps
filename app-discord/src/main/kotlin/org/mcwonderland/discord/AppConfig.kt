package org.mcwonderland.discord

import java.util.*

class AppConfig(private val properties: Properties) : Config {
    override val commandPrefix: String = properties.getProperty("command-prefix")
    override val dbName: String = properties.getProperty("db-name")
    override val mongoConnectionEnv: String = properties.getProperty("mongo-connection")

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}