package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages
) : Command {

    override fun execute(sender: User, args: List<String>): CommandResponse {

        return try {
            val newState = registrationService.toggleRegister(sender)
            ok(if (newState) messages.registered() else messages.unRegistered())
        } catch (e: RequireLinkedAccountException) {
            return fail(messages.requireLinkedAccount())
        }

    }

}