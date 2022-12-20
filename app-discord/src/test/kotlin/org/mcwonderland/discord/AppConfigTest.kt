package org.mcwonderland.discord

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppConfigTest {

    private lateinit var file: File
    private lateinit var config: AppConfig

    @BeforeEach
    fun setup() {
        file = File.createTempFile("config", ".properties")
        file.delete()
    }

    @Test
    fun withoutFileExist_shouldPopulateOneWithDefaultValues() {
        config = AppConfig(file)
        assertNotNull(config.mongoUrl)
    }

    @Test
    fun shouldReplaceEnvVars() {
        System.setProperty("HELLO", "HELLO_VALUE")
        file.writeText("mongo-url=\${HELLO}")

        config = AppConfig(file)

        assertEquals("HELLO_VALUE", config.mongoUrl)
    }

}