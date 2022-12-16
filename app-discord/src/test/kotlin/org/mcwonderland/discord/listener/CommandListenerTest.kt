package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.User
import kotlin.test.Test

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandProcessor: CommandProcessor
    private lateinit var userRepository: UserRepositoryFake

    private lateinit var messageMock: Message

    private val user: User = Dummies.createUserFullFilled()

    private val prefix = "!"
    private val channelId = "channel_id"

    @BeforeEach
    fun setup() {
        commandProcessor = mockk(relaxed = true)
        userRepository = UserRepositoryFake()
        commandListener = CommandListener(commandProcessor, prefix, userRepository)

        messageMock = mockk(relaxed = true)

        userRepository.insertUser(user)

        every { messageMock.author.id } returns user.discordProfile.id
        every { messageMock.author.name } returns user.discordProfile.username
        every { messageMock.channel.id } returns channelId
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
    fun shouldFormatUsers() {
        assertMessageToCommand(
            "!cw command <@user_id>",
            DiscordCommandContext(user, "cw", listOf("command", "user_id"), messageMock.channel)
        )
    }

    @Test
    fun shouldTrimWhitespace() {
        assertMessageToCommand(
            "!cw command  sub",
            DiscordCommandContext(user, "cw", listOf("command", "sub"), messageMock.channel)
        )
    }

    private fun assertMessageToCommand(msg: String, context: DiscordCommandContext) {
        mockMessageAndPrefix(msg)
        sendMessage()
        verify { commandProcessor.onCommand(context) }
    }

    private fun mockMessageAndPrefix(content: String) {
        every { messageMock.isFromGuild } returns true
        every { messageMock.author.isBot } returns false
        every { messageMock.contentRaw } returns content
    }

    private fun assertIgnored() {
        verify(exactly = 0) { commandProcessor.onCommand(any()) }
    }

    private fun sendMessage() {
        val event = MessageReceivedEvent(mockk(relaxed = true), 0, messageMock)
        commandListener.onMessageReceived(event)
    }
}