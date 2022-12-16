package org.mcwonderland.discord.model

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.model.User

data class DiscordCommandContext(
    override val sender: User,
    override val label: String,
    override val args: List<String>,
    val channel: MessageChannelUnion
) : CommandContext {
    fun sendEmbed(embed: MessageEmbed) {
        channel.sendMessageEmbeds(embed).queue()
    }
}