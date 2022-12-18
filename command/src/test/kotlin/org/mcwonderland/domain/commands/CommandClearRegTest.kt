package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService

class CommandClearRegTest : CommandTestBase() {

    private lateinit var registrationService: RegistrationService
    private lateinit var handle: CommandClearRegHandle<CommandContext>

    @BeforeEach
    fun setup() {
        registrationService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandClearReg("clearreg", registrationService, handle)
    }

    @Test
    fun shouldCallService() {
        executeWithNoArgs()
            .also { verify { registrationService.clearRegistrations(sender) } }
            .also { verify { handle.onCleared(context) } }
    }

    @Test
    fun testExceptionMessageMappings() {
        assertExceptionMapping(PermissionDeniedException()) { handle.failPermissionDenied(context, it) }
    }

    private fun <T : Exception> assertExceptionMapping(ex: T, function: (ex: T) -> Unit) {
        every { registrationService.clearRegistrations(sender) } throws ex
        executeWithNoArgs()
        verify { function(ex) }
    }

}