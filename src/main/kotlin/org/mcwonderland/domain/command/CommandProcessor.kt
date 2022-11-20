package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

interface CommandProcessor {
    fun onCommand(sender: CommandSender, label: String, args: List<String>)
}
