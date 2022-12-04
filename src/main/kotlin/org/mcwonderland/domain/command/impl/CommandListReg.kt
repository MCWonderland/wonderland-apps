package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages,
) : Command {
    override fun execute(sender: User, args: List<String>): CommandResponse {
        val users = registrationService.listRegistrations(sender)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.listRegistrations(users)))
    }

}