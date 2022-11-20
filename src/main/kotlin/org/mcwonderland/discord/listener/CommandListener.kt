package org.mcwonderland.discord.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.mcwonderland.domain.command.CommandService
import org.mcwonderland.domain.config.Config

class CommandListener(
    private val commandService: CommandService,
    private val config: Config,
) {

    fun onMessageReceived(event: MessageReceivedEvent) {

        val message = event.message
        val rawMessage = message.contentStripped

        if (!message.isFromGuild
            || message.author.isBot
            || !rawMessage.startsWith(config.commandPrefix)
        ) return


        rawMessage.removePrefix("!").split(" ").let { commandService.onCommand(it[0], it.drop(1)) }
    }


}