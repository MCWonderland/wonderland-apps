package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.features.RegistrationService

class CommandToggleReg(
    override val label: String,
    private val service: RegistrationService,
    private val handle: CommandToggleRegHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
        val state = service.toggleAllowRegistrations(context.sender)

        if (state)
            handle.onEnableRegistrations()
        else
            handle.onDisableRegistrations()
    }

}

interface CommandToggleRegHandle {
    fun onEnableRegistrations()
    fun onDisableRegistrations()
}
