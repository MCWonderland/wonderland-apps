package org.mcwonderland.discord

import io.mockk.mockk
import io.mockk.verify
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import kotlin.test.Test

internal class MessageSenderDiscordGuildTest {

    @Test
    fun shouldSendToGuildChannel() {
        val channel: TextChannel = mockk(relaxed = true)
        val messageSender = MessageSenderDiscordGuild(channel)

        messageSender.sendMessage("Hello World")

        verify { channel.sendMessage("Hello World").queue() }
    }

}