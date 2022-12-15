package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandToggleReg(
    override val label: String,
    private val service: RegistrationService,
    private val handle: CommandToggleRegHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>) {
        val state = service.toggleAllowRegistrations(sender)

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
