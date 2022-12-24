package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
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
    fun testExceptionMessageMappings() {
        executeWithNoArgs().let { verify { handle.failPermissionDenied(context, any()) } }
    }

    @Test
    fun shouldCallService() {
        sender.addAdminPerm()

        executeWithNoArgs()
            .also { verify { registrationService.clearRegistrations() } }
            .also { verify { handle.onCleared(context) } }
    }

}