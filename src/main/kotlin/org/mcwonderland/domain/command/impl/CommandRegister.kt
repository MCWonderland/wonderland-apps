package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val userFinder: UserFinder
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) {
        val user = userFinder.findOrCreate(sender.id)
        registrationService.toggleRegister(user)
    }

}