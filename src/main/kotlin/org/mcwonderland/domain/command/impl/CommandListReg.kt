package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages,
) : Command {
    override fun execute(sender: User, args: List<String>): CommandResponse {

        return try {
            val users = registrationService.listRegistrations(sender)
            ok(messages.listRegistrations(users))
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        }
    }

}