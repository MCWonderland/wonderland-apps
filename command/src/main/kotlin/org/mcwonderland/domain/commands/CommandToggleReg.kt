package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.features.RegistrationService

class CommandToggleReg(
    override val label: String,
    private val service: RegistrationService,
    private val handle: CommandToggleRegHandle<CommandContext>
) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
        val state = service.toggleAllowRegistrations(context.sender)

        if (state)
            handle.onEnableRegistrations(context)
        else
            handle.onDisableRegistrations(context)
    }

}

interface CommandToggleRegHandle<Context : CommandContext> {
    fun onEnableRegistrations(context: Context)
    fun onDisableRegistrations(context: Context)
}
