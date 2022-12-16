package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.features.RegistrationService

class CommandToggleReg<T: CommandContext>(
    override val label: String,
    private val service: RegistrationService,
    private val handle: CommandToggleRegHandle<in CommandContext>
) : Command<T> {
    override val usage: String = "/$label"

    override fun execute(context: T) {
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
