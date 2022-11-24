package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

interface Command {
    val label: String
    val usage: String
        get() = "Usage: /$label"

    fun execute(sender: CommandSender, args: List<String>)

}