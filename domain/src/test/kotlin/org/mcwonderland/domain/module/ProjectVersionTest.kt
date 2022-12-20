package org.mcwonderland.domain.module

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class ProjectVersionTest {

    private val file = File("build/resources/main/version.properties")
    private val version = ProjectVersion()

    @BeforeEach
    fun setup() {
        file.delete()
    }

    @Test
    fun withoutVersionFile_shouldReturnUnknown() {
        assertEquals("unknown", version.get())
    }

    @Test
    fun noVersionKey_shouldReturnUnknown() {
        file.writeText("foo=bar")
        assertEquals("unknown", version.get())
    }

    @Test
    fun withVersionKey_shouldReturnVersion() {
        file.writeText("version=1.0.0")
        assertEquals("1.0.0", version.get())
    }

}