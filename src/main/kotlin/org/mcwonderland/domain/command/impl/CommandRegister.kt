package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val userFinder: UserFinder,
    private val messenger: Messenger,
    private val messages: Messages
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) = runCommand(messenger) {
        val user = userFinder.findOrCreate(sender.id)
        val newState = registrationService.toggleRegister(user)

        when (newState) {
            true -> messenger.sendMessage(messages.registered())
            false -> messenger.sendMessage(messages.unRegistered())
        }
    }

}