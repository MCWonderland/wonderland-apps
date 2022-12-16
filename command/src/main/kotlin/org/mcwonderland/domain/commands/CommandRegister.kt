package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.exceptions.NotAllowRegistrationsException
import org.mcwonderland.domain.exceptions.RequireLinkedAccountException
import org.mcwonderland.domain.features.RegistrationService

class CommandRegister(
    override val label: String,
    private val registrationService: RegistrationService,
    private val handle: CommandRegisterHandle<CommandContext>
) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {

        return try {
            val newState = registrationService.toggleRegister(context.sender)

            if (newState)
                handle.onRegistered(context)
            else
                handle.onUnregistered(context)
        } catch (e: RequireLinkedAccountException) {
            handle.failRequireLinkedAccount(context, e)
        } catch (e: NotAllowRegistrationsException) {
            handle.failNotAllowRegistrations(context, e)
        }

    }

}

interface CommandRegisterHandle<Context : CommandContext> {
    fun onRegistered(context: Context)
    fun onUnregistered(context: Context)
    fun failRequireLinkedAccount(context: Context, e: RequireLinkedAccountException)
    fun failNotAllowRegistrations(context: Context, e: NotAllowRegistrationsException)
}
