package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.User

interface Command {
    val label: String

    fun execute(sender: User, args: List<String>)
}