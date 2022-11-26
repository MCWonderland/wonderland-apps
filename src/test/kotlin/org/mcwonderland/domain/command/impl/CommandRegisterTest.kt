package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.User
import kotlin.test.assertEquals

internal class CommandRegisterTest {

    private lateinit var command: CommandRegister
    private lateinit var registerService: RegistrationService
    private lateinit var userFinder: UserFinder
    private lateinit var messages: Messages
    private lateinit var messenger: MessengerFake

    private val user = User("user")

    @BeforeEach
    fun setUp() {
        registerService = mockk(relaxed = true)
        userFinder = UserFinderStub(user)
        messages = MessagesStub()
        messenger = MessengerFake()

        command = CommandRegister("register", registerService, userFinder, messenger, messages)
    }

    @Test
    fun shouldSendMessageBaseOnState() {
        assertToggleStateMessage(true, messages.registered())
        assertToggleStateMessage(false, messages.unRegistered())
    }

    @Test
    fun onException_shouldSendMessage() {
        val sender = PlatformUser("sender")
        every { registerService.toggleRegister(user) } throws Exception("error")

        command.execute(sender, emptyList())

        assertEquals("error", messenger.lastMessage)
    }

    private fun assertToggleStateMessage(state: Boolean, message: String) {
        val sender = PlatformUser("sender")
        every { registerService.toggleRegister(user) } returns state

        command.execute(sender, emptyList())

        assertEquals(message, messenger.lastMessage)
    }
}