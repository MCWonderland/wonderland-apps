package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.PlatformUser

class CommandProcessorImpl(private val commands: List<Command>) : CommandProcessor {

    override fun onCommand(commandSender: PlatformUser, label: String, args: List<String>): CommandResponse {
        commands.find { it.label == label }?.execute(commandSender, args)
        return CommandResponse(CommandStatus.SUCCESS, emptyList())
    }

}