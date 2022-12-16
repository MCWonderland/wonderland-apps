package org.mcwonderland.domain.command

interface CommandProcessor {
    fun onCommand(context: CommandContext)
}
