package org.mcwonderland.discord

import net.dv8tion.jda.api.JDA
import org.mcwonderland.domain.Messenger

class MessengerDiscordReplyUser(private val jda: JDA, private val channelCache: ChannelCache) : Messenger {

    override fun sendMessage(message: String) {
        if (message.isEmpty())
            return

        channelCache.getLastChannel()?.let { jda.getTextChannelById(it)?.sendMessage(message)?.queue() }
    }

}