package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.commands.CommandRemoveTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.model.Team

class CommandRemoveTeamHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandRemoveTeamHandle<DiscordCommandContext> {

    override fun onSuccess(teamAfterRemoveTarget: Team) {
        commandHistory.sendEmbed(messages.userRemovedFromTeam(teamAfterRemoveTarget))
    }

    override fun failPermissionDenied(e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

    override fun failUserNotFound(e: UserNotFoundException) {
        commandHistory.sendEmbed(messages.userNotFound(e.id))
    }

    override fun failUserNotInTeam(e: UserNotInTeamException) {
        commandHistory.sendEmbed(messages.userNotInTeam(e.user))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        commandHistory.sendEmbed(messages.commandUsage(usage))
    }

}
