package org.mcwonderland.discord.impl

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

class MessengerImpl : Messenger {
    override fun sendMessage(textChannel: MessageChannelUnion, message: String) {
        if (message.isEmpty())
            return

        textChannel.sendMessage(message).queue()
    }

}