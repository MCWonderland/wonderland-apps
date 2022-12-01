package org.mcwonderland.discord

import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.JDA
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

internal class MessengerDiscordReplyUserTest {

    private lateinit var messageSender: MessengerDiscordReplyUser

    private lateinit var jda: JDA
    private lateinit var channelCache: ChannelCache

    @BeforeEach
    fun setup() {
        jda = mockk(relaxed = true)
        channelCache = ChannelCache()
        messageSender = MessengerDiscordReplyUser(jda, channelCache)
    }

    @Test
    fun messageIsEmpty_shouldIgnore() {
        messageSender.sendMessage("")
        verify { jda wasNot Called }
    }

    @Test
    fun noLastChannel_shouldIgnore() {
        messageSender.sendMessage("Hello")
        verify { jda wasNot Called }
    }

    @Test
    fun shouldSendToGuildChannel() {
        channelCache.cache("hello")
        messageSender.sendMessage("Hello World")
        verify { jda.getTextChannelById("hello")!!.sendMessage("Hello World").queue() }
    }

}