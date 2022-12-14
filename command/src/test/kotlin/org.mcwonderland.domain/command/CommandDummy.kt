package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

class CommandDummy(override val label: String) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>) {
    }
}