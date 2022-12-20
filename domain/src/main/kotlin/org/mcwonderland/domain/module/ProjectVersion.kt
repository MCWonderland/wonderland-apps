package org.mcwonderland.domain.module

import java.io.File
import java.util.Properties

class ProjectVersion {

    private val unknownVersion = "unknown"

    fun get(): String {
        val file = getVersionFile() ?: return unknownVersion
        val properties = Properties().apply { load(file.inputStream()) }

        return properties.getProperty("version") ?: unknownVersion
    }

    private fun getVersionFile(): File? {
        return this.javaClass.classLoader.getResource("version.properties")?.file?.let { File(it) }
    }

    private fun unknown(): String {
        return "unknown"
    }
}