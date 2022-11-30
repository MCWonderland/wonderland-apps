package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.PlatformUser
import kotlin.test.assertEquals

internal class CommandRegisterTest : CommandTestBase() {

    private lateinit var registerService: RegistrationService

    @BeforeEach
    fun setUp() {
        registerService = mockk(relaxed = true)
        command = CommandRegister("register", registerService, userFinder, messenger, messages)
    }

    @Test
    fun shouldSendMessageBaseOnState() {
        assertToggleStateMessage(true, messages.registered())
        assertToggleStateMessage(false, messages.unRegistered())
    }

    @Test
    fun onException_shouldSendMessage() {
        every { registerService.toggleRegister(user) } throws Exception("error")

        executeWithNoArgs()

        assertEquals("error", messenger.lastMessage)
    }

    private fun assertToggleStateMessage(state: Boolean, message: String) {
        every { registerService.toggleRegister(user) } returns state

        executeWithNoArgs()

        assertEquals(message, messenger.lastMessage)
    }
}