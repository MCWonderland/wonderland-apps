package org.mcwonderland.domain.command

interface Command {
    val label: String
    val usage: String

    fun execute(context: CommandContext)

}