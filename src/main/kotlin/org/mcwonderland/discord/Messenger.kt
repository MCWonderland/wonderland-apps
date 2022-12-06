package org.mcwonderland.discord

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

interface Messenger {
    fun sendMessage(textChannel: MessageChannelUnion, message: String)
}