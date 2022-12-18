package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandRemoveReg(
    override val label: String,
    private val handle: CommandRemoveRegHandle<CommandContext>,
    private val service: RegistrationService
) : Command {
    override val usage: String = "$label <user>"

    override fun execute(context: CommandContext) {
        try {
            context.checkAdminPermission()
            val target = context.getArg(0) ?: return handle.failWithUsage(context, usage)

            service.removeRegistration(target).let {
                handle.registrationRemoved(context, it)
            }
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(context, e)
        } catch (e: UserNotFoundException) {
            handle.failUserNotFound(context, e)
        }
    }

}

interface CommandRemoveRegHandle<Context : CommandContext> : FailWithUsage<Context>, FailNoPermission<Context> {
    fun registrationRemoved(context: Context, target: User)
    fun failUserNotFound(context: Context, e: UserNotFoundException)
}