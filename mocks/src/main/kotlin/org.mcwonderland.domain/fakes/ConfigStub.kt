package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.config.CommandLabels
import org.mcwonderland.domain.config.CommandLabelsDefault
import org.mcwonderland.domain.config.Config

class ConfigStub : Config {
    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnection: String = "mongodb://localhost:27017"
    override val settingsMongoId: String = "settings"

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}