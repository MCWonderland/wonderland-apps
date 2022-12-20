package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.RegistrationService

class CommandToggleRegTest : CommandTestBase() {

    private lateinit var service: RegistrationService
    private lateinit var handle: CommandToggleRegHandle<CommandContext>

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandToggleReg("togglereg", service, handle)
    }

    @Test
    fun withoutPermission_shouldDenied() {
        executeWithNoArgs().let { verify { handle.failPermissionDenied(context, any()) } }
    }

    @Test
    fun shouldCallRegistrationService() {
        assertToggleMessage(true) { handle.onEnableRegistrations(context) }
        assertToggleMessage(false) { handle.onDisableRegistrations(context) }
    }

    private fun assertToggleMessage(state: Boolean, toggle: () -> Unit) {
        every { service.toggleAllowRegistrations() } returns state
        sender.addAdminPerm()
        executeWithNoArgs()
        verify { toggle() }
    }


}