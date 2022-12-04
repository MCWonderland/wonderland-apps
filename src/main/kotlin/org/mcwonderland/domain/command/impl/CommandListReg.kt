package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages,
    private val userFinder: UserFinder
) : Command {
    override fun execute(sender: PlatformUser, args: List<String>): CommandResponse {
        val executor = userFinder.findOrCreate(sender.id)
        val users = registrationService.listRegistrations(executor)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.listRegistrations(users)))
    }

}