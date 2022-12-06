package org.mcwonderland.domain.fakes

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import org.mcwonderland.discord.Messenger

class MessengerFake : Messenger {

    val messages: MutableList<String> = mutableListOf()
    var lastMessage: String = ""
        private set

    override fun sendMessage(textChannel: MessageChannelUnion, message: String) {
        messages.add(message)
        lastMessage = message
    }

}