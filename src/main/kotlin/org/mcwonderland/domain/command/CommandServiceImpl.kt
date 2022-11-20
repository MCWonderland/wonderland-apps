package org.mcwonderland.domain.command

class CommandServiceImpl(private val commands: List<Command>) : CommandService {

    override fun onCommand(label: String, args: List<String>) {
        commands.find { it.label == label }?.execute(label, args)
    }

}