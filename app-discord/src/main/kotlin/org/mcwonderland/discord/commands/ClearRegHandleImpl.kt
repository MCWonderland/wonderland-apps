package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandClearRegHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException

class ClearRegHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandClearRegHandle<DiscordCommandContext> {

    override fun onCleared(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.registrationsCleared())
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

}
