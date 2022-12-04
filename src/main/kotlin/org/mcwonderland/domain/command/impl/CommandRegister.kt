package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
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
            return fail(e.message ?: "Unknown Error")
        }

        return ok(if (newState) messages.registered() else messages.unRegistered())
    }

}