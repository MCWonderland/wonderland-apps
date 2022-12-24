package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.commands.CommandClearTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException

class ClearTeamsHandleImpl(private val messages: Messages) : CommandClearTeamHandle<DiscordCommandContext> {

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }

    override fun success(context: DiscordCommandContext, clearAmount: Int) {
        context.sendEmbed(messages.teamsCleared(clearAmount))
    }

}
