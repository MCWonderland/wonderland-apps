package org.mcwonderland.discord.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.mcwonderland.domain.command.CommandProcessor
import org.mcwonderland.domain.config.Config
import org.mcwonderland.domain.model.PlatformUser

class CommandListener(
    private val commandProcessor: CommandProcessor,
    private val config: Config,
) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {

        val message = event.message
        val rawMessage = message.contentStripped
        val author = message.author

        if (!message.isFromGuild
            || message.author.isBot
            || !rawMessage.startsWith(config.commandPrefix)
        ) return


        rawMessage
            .removePrefix("!")
            .split(" ")
            .let {
                commandProcessor.onCommand(
                    PlatformUser(author.id),
                    it[0],
                    it.drop(1)
                )
            }
    }
}