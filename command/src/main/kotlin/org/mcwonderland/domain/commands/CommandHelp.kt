package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext

class CommandHelp(
    override val label: String,
    private val commands: List<Command>,
    private val handle: CommandHelpHandle<CommandContext>
) : Command {
    override val usage: String = "$label"

    override fun execute(context: CommandContext) {
        return handle.showHelp(context, commands)
    }

}

interface CommandHelpHandle<Context : CommandContext> {
    fun showHelp(context: Context, commands: List<Command>)
}