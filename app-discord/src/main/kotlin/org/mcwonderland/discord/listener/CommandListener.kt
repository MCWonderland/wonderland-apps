package org.mcwonderland.discord.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.mcwonderland.discord.CommandHistory
import org.mcwonderland.discord.CommandRecord
import org.mcwonderland.discord.impl.Messenger
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.model.DiscordProfile
import org.mcwonderland.domain.repository.UserRepository

class CommandListener(
    private val commandProcessor: CommandProcessor,
    private val prefix: String,
    private val userRepository: UserRepository,
    private val commandHistory: CommandHistory
) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        val rawMessage = message.contentRaw
        val author = message.author

        if (!message.isFromGuild
            || message.author.isBot
            || !rawMessage.startsWith(prefix)
        ) return

        val splits = rawMessage.removePrefix("!").removeTrailingSpaces().split(" ")
        val label = splits[0]
        val args = splits.drop(1).map { formatArgs(it) }

        val user = userRepository.findUpdated(DiscordProfile(author.id, author.name))

        commandProcessor.onCommand(user, label, args)
        commandHistory.add(CommandRecord(event.channel, user))
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