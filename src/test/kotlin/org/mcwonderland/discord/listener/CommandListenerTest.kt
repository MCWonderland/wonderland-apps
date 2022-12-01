package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.discord.ChannelCache
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.fakes.ConfigStub
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandProcessor: CommandProcessor
    private lateinit var channelCache: ChannelCache
    private lateinit var config: Config

    private lateinit var messageMock: Message

    private val commandSender = Dummies.createCommandSender()
    private val channelId = "channel_id"

    @BeforeEach
    fun setup() {
        commandProcessor = mockk(relaxed = true)
        channelCache = ChannelCache()
        config = ConfigStub()
        commandListener = CommandListener(commandProcessor, channelCache, config)

        messageMock = mockk(relaxed = true)
        every { messageMock.author.id } returns commandSender.id
        every { messageMock.channel.id } returns channelId
    }

    @Test
    fun shouldCacheChannel() {
        sendMessage()
        assertEquals(channelId, channelCache.getLastChannel())
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
        every { messageMock.contentRaw } returns "?not start with cmd prefix"

        sendMessage()

        assertIgnored()
    }

    @Test
    fun startWithCmdPrefix_shouldCallCommandService() {
        mockMessageAndPrefix("!cw command sub")

        sendMessage()

        verify { commandProcessor.onCommand(commandSender, "cw", listOf("command", "sub")) }
    }

    @Test
    fun shouldFormatUsers() {
        assertMessageToCommand("!cw command <@user_id>", label = "cw", args = listOf("command", "user_id"))
    }

    @Test
    fun shouldTrimWhitespace() {
        assertMessageToCommand("!cw command  sub", label = "cw", args = listOf("command", "sub"))
    }

    private fun assertMessageToCommand(msg: String, label: String, args: List<String>) {
        mockMessageAndPrefix(msg)
        sendMessage()
        verify { commandProcessor.onCommand(commandSender, label, args) }
    }

    private fun mockMessageAndPrefix(content: String) {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { messageMock.contentRaw } returns content
    }

    private fun assertIgnored() {
        verify(exactly = 0) { commandProcessor.onCommand(any(), any(), any()) }
    }

    private fun sendMessage() {
        val event = MessageReceivedEvent(mockk(relaxed = true), 0, messageMock)
        commandListener.onMessageReceived(event)
    }
}