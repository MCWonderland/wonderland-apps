package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

interface Command {
    val label: String

    fun execute(sender: CommandSender, args: List<String>)
}