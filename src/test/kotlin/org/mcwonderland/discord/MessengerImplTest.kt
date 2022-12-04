package org.mcwonderland.discord

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

internal class MessengerImplTest {

    private lateinit var messageSender: MessengerImpl

    private lateinit var channel: MessageChannelUnion

    @BeforeEach
    fun setup() {
        channel = mockk(relaxed = true)

        messageSender = MessengerImpl()
    }

    @Test
    fun messageIsEmpty_shouldIgnore() {
        messageSender.sendMessage(channel, "")
        verify { channel wasNot Called }
    }

    @Test
    fun shouldSendToGuildChannel() {
        val message = "Hello World"
        messageSender.sendMessage(channel, message)
        verify { channel.sendMessage(message).queue() }
    }

}