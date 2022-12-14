package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.commands.CommandToggleReg

class CommandToggleRegTest : CommandTestBase() {

    private lateinit var service: RegistrationService

    @BeforeEach
    fun setup() {
        service = mockk(relaxed = true)
        command = CommandToggleReg("togglereg", service, messages)
    }

    @Test
    fun shouldCallRegistrationService() {
        assertToggleMessage(true, messages.nowAcceptRegistrations())
        assertToggleMessage(false, messages.noLongerAcceptRegistrations())
    }

    private fun assertToggleMessage(state: Boolean, msg: String) {
        every { service.toggleAllowRegistrations(sender) } returns state
        executeWithNoArgs().assertSuccess(msg)
    }


}