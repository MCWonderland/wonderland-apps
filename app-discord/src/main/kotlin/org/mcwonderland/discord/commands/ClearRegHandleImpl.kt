package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandClearRegHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException

class ClearRegHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandClearRegHandle {

    override fun onCleared() {
        commandHistory.sendEmbed(messages.registrationsCleared())
    }

    override fun failPermissionDenied(e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

}
