package org.mcwonderland.domain.command

import org.mcwonderland.domain.model.PlatformUser

interface CommandProcessor {
    fun onCommand(sender: PlatformUser, label: String, args: List<String>): CommandResponse
}
