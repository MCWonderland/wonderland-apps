package org.mcwonderland

import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.CommandLabelsDefault
import org.mcwonderland.domain.config.Config
import java.util.*

class AppConfig(private val properties: Properties) : Config {
    override val commandPrefix: String = properties.getProperty("command-prefix")
    override val dbName: String = properties.getProperty("db-name")
    override val mongoConnection: String = properties.getProperty("mongo-connection")
    override val settingsMongoId: String = properties.getProperty("mongo-settings-id")

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}