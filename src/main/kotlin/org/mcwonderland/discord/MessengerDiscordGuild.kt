package org.mcwonderland.discord

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import org.mcwonderland.domain.Messenger

class MessengerDiscordGuild(private val channel: TextChannel) : Messenger {

    override fun sendMessage(message: String) {
        channel.sendMessage(message).queue()
    }

}