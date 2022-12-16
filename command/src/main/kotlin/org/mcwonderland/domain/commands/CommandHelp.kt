package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext

class CommandHelp<T : CommandContext>(
    override val label: String,
    private val commands: List<Command<*>>,
    private val handle: CommandHelpHandle<in CommandContext>
) : Command<T> {
    override val usage: String = "/$label"

    override fun execute(context: T) {
        return handle.showHelp(context, commands)
    }

}

interface CommandHelpHandle<Context : CommandContext> {
    fun showHelp(context: Context, commands: List<Command<*>>)
}