package org.mcwonderland.domain.command

import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.User

class CommandProcessorImpl(
    private val commands: List<Command>,
    private val messages: Messages
) : CommandProcessor {

    override fun onCommand(sender: User, label: String, args: List<String>): CommandResponse? {
        return try {
            commands.find { it.label == label }?.execute(sender, args)
        } catch (e: Exception) {
            CommandResponse(CommandStatus.FAILURE, listOf(messages.unHandledCommandError(e::class.java.simpleName)))
        }
    }

}