package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService

class CommandClearReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandClearRegHandle
) : Command {

    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
        return try {
            registrationService.clearRegistrations(context.sender)
            handle.onCleared()
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(e)
        }
    }

}

interface CommandClearRegHandle : FailNoPermission {
    fun onCleared()
}