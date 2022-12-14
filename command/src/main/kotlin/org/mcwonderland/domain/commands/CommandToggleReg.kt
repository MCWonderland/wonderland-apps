package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

class CommandToggleReg(
    override val label: String,
    private val service: RegistrationService,
    private val messages: Messages
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val state = service.toggleAllowRegistrations(sender)

        return ok(
            if (state) messages.nowAcceptRegistrations()
            else messages.noLongerAcceptRegistrations()
        )
    }

}