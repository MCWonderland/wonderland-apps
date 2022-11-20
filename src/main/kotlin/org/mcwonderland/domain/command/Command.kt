package org.mcwonderland.domain.command

interface Command {
    val label: String

    fun execute(label: String, args: List<String>)
}