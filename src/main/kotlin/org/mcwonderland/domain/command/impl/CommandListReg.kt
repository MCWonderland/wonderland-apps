package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.PlatformUser

class CommandListReg(
    override val label: String,
    private val registrationService: RegistrationService,
    private val messages: Messages,
    private val messenger: Messenger
) : Command {
    override fun execute(sender: PlatformUser, args: List<String>) {
        val users = registrationService.listRegistrations()
        messenger.sendMessage(messages.listRegistrations(users))
    }

}