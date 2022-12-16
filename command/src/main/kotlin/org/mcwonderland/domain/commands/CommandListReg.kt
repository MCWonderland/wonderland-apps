package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandListRegHandle<CommandContext>
) : Command {
    override val usage: String = "$label"

    override fun execute(context: CommandContext) {

        return try {
            context.checkAdminPermission()
            val users = registrationService.listRegistrations()
            handle.success(context, users)
        } catch (e: PermissionDeniedException) {
            handle.failNoPermission(context)
        }
    }

}

interface CommandListRegHandle<Context : CommandContext> {
    fun failNoPermission(context: Context)
    fun success(context: Context, users: Collection<User>)
}