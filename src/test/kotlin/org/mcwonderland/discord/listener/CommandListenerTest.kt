package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.config.Config
import kotlin.test.Test

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandProcessor: CommandProcessor
    private lateinit var config: Config

    private lateinit var messageMock: Message

    private val commandSender = Dummies.createCommandSender()

    @BeforeEach
    fun setup() {
        commandProcessor = mockk(relaxed = true)
        config = mockk()
        commandListener = CommandListener(commandProcessor, config)

        messageMock = mockk(relaxed = true)
        every { messageMock.author.id } returns commandSender.id
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
        every { messageMock.contentRaw } returns "!cw command sub"

        sendMessage()

        verify { commandProcessor.onCommand(commandSender, "cw", listOf("command", "sub")) }
    }

    @Test
    fun shouldFormatUsers() {
        val id = "1234567890"

        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { config.commandPrefix } returns "!"
        every { messageMock.contentRaw } returns "!cw command <@$id>"

        sendMessage()

        verify { commandProcessor.onCommand(commandSender, "cw", listOf("command", id)) }
    }


    private fun assertIgnored() {
        verify(exactly = 0) { commandProcessor.onCommand(any(), any(), any()) }
    }

    private fun sendMessage() {
        val event = MessageReceivedEvent(mockk(relaxed = true), 0, messageMock)
        commandListener.onMessageReceived(event)
    }
}