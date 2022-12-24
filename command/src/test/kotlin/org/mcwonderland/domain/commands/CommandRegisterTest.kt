package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.features.RegistrationService

internal class CommandRegisterTest : CommandTestBase() {

    private lateinit var registerService: RegistrationService
    private lateinit var handle: CommandRegisterHandle<CommandContext>

    @BeforeEach
    fun setUp() {
        registerService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandRegister("register", registerService, handle)
    }

    @Test
    fun shouldSendMessageBaseOnState() {
        assertStateToggled(true) { handle.onRegistered(context) }
        assertStateToggled(false) { handle.onUnregistered(context) }
    }

    @Test
    fun testExceptionMapping() {
        assertExceptionMapping(RequireLinkedAccountException()) { handle.failRequireLinkedAccount(context, it) }
        assertExceptionMapping(NotAllowRegistrationsException()) { handle.failNotAllowRegistrations(context, it) }
    }

    private fun <T : Exception> assertExceptionMapping(exception: T, block: (T) -> Unit) {
        every { registerService.toggleRegister(sender) } throws exception
        executeWithNoArgs()
        verify { block(exception) }
    }

    private fun assertStateToggled(state: Boolean, block: () -> Unit) {
        every { registerService.toggleRegister(sender) } returns state
        executeWithNoArgs()
        verify { block() }
    }
}