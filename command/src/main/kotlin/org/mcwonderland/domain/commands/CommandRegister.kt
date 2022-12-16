package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.features.RegistrationService

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandRegisterHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {

        return try {
            val newState = registrationService.toggleRegister(context.sender)

            if (newState)
                handle.onRegistered()
            else
                handle.onUnregistered()
        } catch (e: RequireLinkedAccountException) {
            handle.failRequireLinkedAccount(e)
        } catch (e: NotAllowRegistrationsException) {
            handle.failNotAllowRegistrations(e)
        }

    }

}

interface CommandRegisterHandle {
    fun onRegistered()
    fun onUnregistered()
    fun failRequireLinkedAccount(e: RequireLinkedAccountException)
    fun failNotAllowRegistrations(e: NotAllowRegistrationsException)
}
