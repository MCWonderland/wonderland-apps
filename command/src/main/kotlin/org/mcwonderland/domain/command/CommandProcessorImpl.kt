package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

class CommandProcessorImpl(
    private val commands: List<Command>,
) : CommandProcessor {

    override fun onCommand(sender: User, label: String, args: List<String>) {
        commands.find { it.label == label }?.execute(sender, args)
    }

}