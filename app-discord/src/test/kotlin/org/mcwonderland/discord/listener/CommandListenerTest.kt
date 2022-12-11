package org.mcwonderland.discord.listener

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.discord.MessengerFake
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.User
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CommandListenerTest {

    private lateinit var commandListener: CommandListener
    private lateinit var commandProcessor: CommandProcessor
    private lateinit var userRepository: UserRepositoryFake

    private lateinit var messageMock: Message
    private lateinit var messenger: MessengerFake

    private val user: User = Dummies.createUserFullFilled()

    private val prefix = "!"
    private val channelId = "channel_id"

    @BeforeEach
    fun setup() {
        commandProcessor = mockk(relaxed = true)
        messenger = MessengerFake()
        userRepository = UserRepositoryFake()
        commandListener = CommandListener(commandProcessor, prefix, messenger, userRepository)

        messageMock = mockk(relaxed = true)

        userRepository.insertUser(user)

        every { messageMock.author.id } returns user.discordId
        every { messageMock.author.name } returns user.discordUsername
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
    fun startWithCmdPrefix_shouldCallCommandService() {
        mockMessageAndPrefix("!cw command sub")

        sendMessage()

        verify { commandProcessor.onCommand(user, "cw", listOf("command", "sub")) }
    }

    @Test
    fun shouldFormatUsers() {
        assertMessageToCommand("!cw command <@user_id>", label = "cw", args = listOf("command", "user_id"))
    }

    @Test
    fun shouldTrimWhitespace() {
        assertMessageToCommand("!cw command  sub", label = "cw", args = listOf("command", "sub"))
    }

    @Test
    fun shouldSendMessages() {
        mockMessageAndPrefix("!cw command sub")

        val response = CommandResponse(
            status = CommandStatus.SUCCESS,
            messages = listOf(
                "message1",
                "message2"
            )
        )

        every { commandProcessor.onCommand(user, "cw", listOf("command", "sub")) } returns response

        sendMessage()

        assertEquals(response.messages, messenger.messages)
    }

    private fun assertMessageToCommand(msg: String, label: String, args: List<String>) {
        mockMessageAndPrefix(msg)
        sendMessage()
        verify { commandProcessor.onCommand(user, label, args) }
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