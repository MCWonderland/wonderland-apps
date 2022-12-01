package org.mcwonderland.discord.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.mcwonderland.discord.ChannelCache
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.PlatformUser

class CommandListener(
    private val commandProcessor: CommandProcessor,
    private val channelCache: ChannelCache,
    private val config: Config,
) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        val rawMessage = message.contentRaw
        val author = message.author

        channelCache.cache(message.channel.id)

        if (!message.isFromGuild
            || message.author.isBot
            || !rawMessage.startsWith(config.commandPrefix)
        ) return

        val splits = rawMessage.removePrefix("!").removeTrailingSpaces().split(" ")
        val label = splits[0]
        val args = splits.drop(1).map { formatArgs(it) }

        commandProcessor.onCommand(PlatformUser(author.id), label, args)
    }

    private fun String.removeTrailingSpaces(): String {
        return this.trim().replace(" +".toRegex(), " ")
    }

    private fun formatArgs(it: String): String {
        if (it.startsWith("<@") && it.endsWith(">"))
            return it.removePrefix("<@").removeSuffix(">")

        return it
    }
}