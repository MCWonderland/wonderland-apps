package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandClearReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages
) : Command {

    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        return try {
            registrationService.clearRegistrations(sender)
            ok(messages.registrationsCleared())
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        }
    }

}