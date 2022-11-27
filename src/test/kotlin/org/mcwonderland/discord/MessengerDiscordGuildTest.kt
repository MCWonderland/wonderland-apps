package org.mcwonderland.discord

import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

internal class MessengerDiscordGuildTest {

    private lateinit var channel: TextChannel
    private lateinit var messageSender: MessengerDiscordGuild

    @BeforeEach
    fun setup() {
        channel = mockk(relaxed = true)
        messageSender = MessengerDiscordGuild(channel)
    }

    @Test
    fun shouldSendToGuildChannel() {
        messageSender.sendMessage("Hello World")
        verify { channel.sendMessage("Hello World").queue() }
    }

    @Test
    fun messageIsEmpty_shouldIgnore() {
        messageSender.sendMessage("")
        verify(exactly = 0) { channel.sendMessage(any<String>()).queue() }
    }

}