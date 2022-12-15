package org.mcwonderland.discord

import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.CommandLabelsDefault
import org.mcwonderland.discord.config.Config

class ConfigStub : Config {
    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnectionEnv: String = "mongodb://localhost:27017"

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}