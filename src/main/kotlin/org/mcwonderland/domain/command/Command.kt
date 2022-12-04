package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

interface Command {
    val label: String
    val usage: String
        get() = "Usage: /$label"

    fun execute(sender: User, args: List<String>): CommandResponse
}