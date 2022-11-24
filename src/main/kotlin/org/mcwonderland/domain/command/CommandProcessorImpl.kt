package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

class CommandProcessorImpl(private val commands: List<Command>) : CommandProcessor {

    override fun onCommand(commandSender: CommandSender, label: String, args: List<String>) {
        commands.find { it.label == label }?.execute(commandSender, args)
    }

}