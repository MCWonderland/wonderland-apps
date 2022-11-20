package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

class CommandServiceImpl(private val commands: List<Command>) : CommandService {

    override fun onCommand(commandSender: CommandSender, label: String, args: List<String>) {
        commands.find { it.label == label }?.execute(commandSender, args)
    }

}