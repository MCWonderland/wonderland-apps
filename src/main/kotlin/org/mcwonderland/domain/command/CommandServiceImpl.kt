package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

class CommandServiceImpl(private val commands: List<Command>) : CommandService {

    override fun onCommand(user: User, label: String, args: List<String>) {
        commands.find { it.label == label }?.execute(user, args)
    }

}