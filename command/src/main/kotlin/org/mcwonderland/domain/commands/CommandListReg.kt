package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandListRegHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>) {

        return try {
            sender.checkAdminPermission()
            val users = registrationService.listRegistrations()
            handle.success(users)
        } catch (e: PermissionDeniedException) {
            handle.failNoPermission()
        }
    }

}

interface CommandListRegHandle {
    fun failNoPermission()
    fun success(users: Collection<User>)
}