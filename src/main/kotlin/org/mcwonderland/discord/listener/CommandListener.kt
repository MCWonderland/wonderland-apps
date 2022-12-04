package org.mcwonderland.discord.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.mcwonderland.discord.Messenger
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.features.UserFinder

class CommandListener(
    private val commandProcessor: CommandProcessor,
    private val config: Config,
    private val messenger: Messenger,
    private val userFinder: UserFinder
) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        val rawMessage = message.contentRaw
        val author = message.author


        if (!message.isFromGuild
            || message.author.isBot
            || !rawMessage.startsWith(config.commandPrefix)
        ) return

        val splits = rawMessage.removePrefix("!").removeTrailingSpaces().split(" ")
        val label = splits[0]
        val args = splits.drop(1).map { formatArgs(it) }

        commandProcessor.onCommand(userFinder.findOrCreate(author.id), label, args)?.let {
            it.messages.forEach { msg -> messenger.sendMessage(event.channel, msg) }
        }
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