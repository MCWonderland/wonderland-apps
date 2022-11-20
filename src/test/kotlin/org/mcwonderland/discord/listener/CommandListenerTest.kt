package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.command.CommandService
import org.mcwonderland.domain.config.Config
import kotlin.test.Test

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandService: CommandService
    private lateinit var config: Config

    private lateinit var messageMock: Message

    @BeforeEach
    fun setup() {
        commandService = mockk(relaxed = true)
        config = mockk()
        commandListener = CommandListener(commandService, config)

        messageMock = mockk(relaxed = true)
    }

    @Test
    fun notInGuild_shouldIgnore() {
        every { messageMock.isFromGuild } returns false

        sendMessage()

        verify(exactly = 0) { commandService.onCommand(any(), any()) }
    }

    @Test
    fun senderIsBot_shouldIgnore() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns true

        sendMessage()

        verify(exactly = 0) { commandService.onCommand(any(), any()) }
    }

    @Test
    fun notStartWithCmdPrefix_shouldIgnore() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { config.commandPrefix } returns "!"
        every { messageMock.contentRaw } returns "?not start with cmd prefix"

        sendMessage()

        verify(exactly = 0) { commandService.onCommand(any(), any()) }
    }

    @Test
    fun startWithCmdPrefix_shouldCallCommandService() {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { config.commandPrefix } returns "!"
        every { messageMock.contentStripped } returns "!cw command sub"

        sendMessage()

        verify { commandService.onCommand("cw", listOf("command", "sub")) }
    }

    private fun sendMessage() {
        val event = MessageReceivedEvent(mockk(relaxed = true), 0, messageMock)
        commandListener.onMessageReceived(event)
    }
}