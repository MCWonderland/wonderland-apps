package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.model.User

class CommandHelp(
    override val label: String,
    private val commands: List<Command>,
    private val handle: CommandHelpHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>) {
        return handle.showHelp(commands)
    }

}

interface CommandHelpHandle {
    fun showHelp(commands: List<Command>)
}
