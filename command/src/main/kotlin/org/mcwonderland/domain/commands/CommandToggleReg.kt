package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService

class CommandToggleReg(
    override val label: String,
    private val service: RegistrationService,
    private val handle: CommandToggleRegHandle<CommandContext>
) : Command {
    override val usage: String = "$label"

    override fun execute(context: CommandContext) {
        try {
            context.checkAdminPermission()

            val state = service.toggleAllowRegistrations()

            if (state)
                handle.onEnableRegistrations(context)
            else
                handle.onDisableRegistrations(context)
        } catch (e: PermissionDeniedException){
            handle.failPermissionDenied(context, e)
        }
    }

}

interface CommandToggleRegHandle<Context : CommandContext> : FailNoPermission<Context> {
    fun onEnableRegistrations(context: Context)
    fun onDisableRegistrations(context: Context)
}
