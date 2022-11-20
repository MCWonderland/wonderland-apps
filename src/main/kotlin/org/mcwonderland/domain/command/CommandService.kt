package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.CommandSender

interface CommandService {
    fun onCommand(sender: CommandSender, label: String, args: List<String>)
}
