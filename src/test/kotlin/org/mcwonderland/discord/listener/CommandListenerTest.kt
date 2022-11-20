package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.command.CommandService
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.User
import kotlin.test.Test

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandService: CommandService
    private lateinit var config: Config

    private lateinit var messageMock: Message

    private val user = User("user_id")

    @BeforeEach
    fun setup() {
        commandService = mockk(relaxed = true)
        config = mockk()
        commandListener = CommandListener(commandService, config)

        messageMock = mockk(relaxed = true)
        every { messageMock.author.id } returns user.id
    }

    @Test
    fun notInGuild_shouldIgnore() {
        every { messageMock.isFromGuild } returns false

        sendMessage()
        assertIgnored()
    }

    @Test
    fun senderIsBot_shouldIgnore() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns true

        sendMessage()

        assertIgnored()
    }

    @Test
    fun notStartWithCmdPrefix_shouldIgnore() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { config.commandPrefix } returns "!"
        every { messageMock.contentRaw } returns "?not start with cmd prefix"

        sendMessage()

        assertIgnored()
    }

    @Test
    fun startWithCmdPrefix_shouldCallCommandService() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { config.commandPrefix } returns "!"
        every { messageMock.contentStripped } returns "!cw command sub"

        sendMessage()

        verify { commandService.onCommand(user, "cw", listOf("command", "sub")) }
    }

    private fun assertIgnored() {
        verify(exactly = 0) { commandService.onCommand(any(), any(), any()) }
    }

    private fun sendMessage() {
        val event = MessageReceivedEvent(mockk(relaxed = true), 0, messageMock)
        commandListener.onMessageReceived(event)
    }
}