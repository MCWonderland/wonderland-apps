package org.mcwonderland.domain.command

class CommandDummy(override val label: String) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
    }
}