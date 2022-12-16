package org.mcwonderland.domain.command

interface Command<T : CommandContext> {
    val label: String
    val usage: String

    fun execute(context: T)

}