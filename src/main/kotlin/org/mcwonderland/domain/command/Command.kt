package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.PlatformUser

interface Command {
    val label: String
    val usage: String
        get() = "Usage: /$label"

    fun execute(sender: PlatformUser, args: List<String>): CommandResponse
}