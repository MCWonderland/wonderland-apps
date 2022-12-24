package org.mcwonderland.domain.module

import java.io.InputStream
import java.util.Properties

class ProjectVersion(private val classLoader: ClassLoader) {

    private val unknownVersion = "unknown"

    fun get(): String {
        val inputStream = getVersionResource() ?: return unknownVersion

        val properties = Properties().apply { load(inputStream) }

        return properties.getProperty("version") ?: unknownVersion
    }

    private fun getVersionResource(): InputStream? {
        return classLoader.getResourceAsStream("version.properties")
    }

}