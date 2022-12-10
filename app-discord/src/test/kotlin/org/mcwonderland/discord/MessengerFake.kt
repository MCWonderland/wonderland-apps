package org.mcwonderland.discord

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import org.mcwonderland.discord.impl.Messenger

class MessengerFake : Messenger {

    val messages: MutableList<String> = mutableListOf()
    var lastMessage: String = ""
        private set

    override fun sendMessage(textChannel: MessageChannelUnion, message: String) {
        messages.add(message)
        lastMessage = message
    }

}