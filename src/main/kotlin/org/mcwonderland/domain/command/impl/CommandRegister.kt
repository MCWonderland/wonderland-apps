package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages
) : Command {

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val newState = try {
            registrationService.toggleRegister(sender)
        } catch (e: Exception) {
            return CommandResponse(CommandStatus.FAILURE, listOf(e.message ?: "Unknown error"))
        }

        return when (newState) {
            true -> CommandResponse(CommandStatus.SUCCESS, listOf(messages.registered()))
            false -> CommandResponse(CommandStatus.SUCCESS, listOf(messages.unRegistered()))
        }
    }

}