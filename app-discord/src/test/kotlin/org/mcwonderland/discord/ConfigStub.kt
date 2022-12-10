package org.mcwonderland.discord

class ConfigStub : Config {
    override val commandPrefix: String = "!"
    override val dbName: String = "mcwonderland"
    override val mongoConnectionEnv: String = "mongodb://localhost:27017"

    override val commandLabels: CommandLabels = CommandLabelsDefault()
}