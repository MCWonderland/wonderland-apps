package org.mcwonderland.discord

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import org.mcwonderland.domain.MessageSender

class MessageSenderDiscordGuild(private val channel: TextChannel) : MessageSender {

    override fun sendMessage(message: String) {
        channel.sendMessage(message).queue()
    }

}