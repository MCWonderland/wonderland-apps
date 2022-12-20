package org.mcwonderland.discord

import org.mcwonderland.discord.config.CommandLabels
import org.mcwonderland.discord.config.CommandLabelsDefault
import org.mcwonderland.discord.config.Config
import java.io.File
import java.util.*

class AppConfig(private val file: File) : Config {
    override val commandPrefix: String = load("command-prefix", "!")
    override val dbName: String = load("db-name", "mongo-db-collection")
    override val mongoUrl: String = load("mongo-url", "MONGO_CONNECTION_STRING")
    override val commandLabels: CommandLabels = CommandLabelsDefault()

    init {
        loadConfig()
    }

    private val properties: Properties = Properties()
    private var shouldSave = false


    private fun loadConfig() {
        if (!file.exists())
            file.createNewFile()

        properties.load(file.inputStream())

        if (shouldSave)
            properties.store(file.outputStream(), null)
    }

    private fun load(key: String, default: String): String {
        println("load")
        val value = properties.getProperty(key, default)
        properties.setProperty(key, value)
        shouldSave = true

        return value.replaceEnvVars()
    }
}

private fun String.replaceEnvVars(): String {
    val regex = Regex("\\$\\{([^}]+)}")

    val matches = regex.findAll(this)
    var result = this

    for (match in matches) {
        val envVar = match.groupValues[1]
        val envVarValue = System.getenv(envVar) ?: System.getProperty(envVar)
        if (envVarValue != null)
            result = result.replace(match.value, envVarValue)
    }

    return result
}
