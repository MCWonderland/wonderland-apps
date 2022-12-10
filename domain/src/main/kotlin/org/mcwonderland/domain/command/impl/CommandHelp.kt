package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.model.User

class CommandHelp(
    override val label: String,
    private val commands: List<Command>,
    private val messages: Messages
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        return ok(messages.commandHelp(commands))
    }

}