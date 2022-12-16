package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandClearRegHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException

class ClearRegHandleImpl(
    private val messages: Messages,
) : CommandClearRegHandle<DiscordCommandContext> {

    override fun onCleared(context: DiscordCommandContext) {
        context.sendEmbed(messages.registrationsCleared())
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }

}
