package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService

class CommandClearReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandClearRegHandle<CommandContext>
) : Command {

    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
        return try {
            registrationService.clearRegistrations(context.sender)
            handle.onCleared(context)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(context, e)
        }
    }

}

interface CommandClearRegHandle<Context : CommandContext> : FailNoPermission<Context> {
    fun onCleared(context: Context)
}