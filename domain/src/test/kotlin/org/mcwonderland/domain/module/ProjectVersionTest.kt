package org.mcwonderland.domain.module

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectVersionTest {

    private lateinit var classLoader: ClassLoader
    private lateinit var version: ProjectVersion

    @BeforeEach
    fun setup() {
        classLoader = mockk(relaxed = true)
        version = ProjectVersion(classLoader)
    }

    @Test
    fun withoutVersionFile_shouldReturnUnknown() {
        mockVersionFile(null)
        assertEquals("unknown", version.get())
    }

    @Test
    fun noVersionKey_shouldReturnUnknown() {
        mockVersionFile("foo=bar")
        assertEquals("unknown", version.get())
    }

    @Test
    fun withVersionKey_shouldReturnVersion() {
        mockVersionFile("version=1.0.0")
        assertEquals("1.0.0", version.get())
    }

    private fun mockVersionFile(content: String?) {
        every { classLoader.getResourceAsStream("version.properties") } returns content?.byteInputStream()
    }

}