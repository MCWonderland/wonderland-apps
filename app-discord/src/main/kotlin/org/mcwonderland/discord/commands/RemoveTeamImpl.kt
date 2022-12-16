package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandRemoveTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.model.Team

class CommandRemoveTeamHandleImpl(private val messages: Messages) : CommandRemoveTeamHandle<DiscordCommandContext> {

    override fun onSuccess(context: DiscordCommandContext, teamAfterRemoveTarget: Team) {
        context.sendEmbed(messages.userRemovedFromTeam(teamAfterRemoveTarget))
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }

    override fun failUserNotFound(context: DiscordCommandContext, e: UserNotFoundException) {
        context.sendEmbed(messages.userNotFound(e.id))
    }

    override fun failUserNotInTeam(context: DiscordCommandContext, e: UserNotInTeamException) {
        context.sendEmbed(messages.userNotInTeam(e.user))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        context.sendEmbed(messages.commandUsage(usage))
    }

}
