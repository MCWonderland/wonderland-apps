package org.mcwonderland.domain.command

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.model.PlatformUser

interface Command {
    val label: String
    val usage: String
        get() = "Usage: /$label"

    fun execute(sender: PlatformUser, args: List<String>)

    fun runCommand(messenger: Messenger, block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            messenger.sendMessage(e.message ?: "Unknown error")
        }
    }
}