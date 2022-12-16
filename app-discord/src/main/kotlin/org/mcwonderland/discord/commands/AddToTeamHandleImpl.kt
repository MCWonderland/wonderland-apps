package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandAddToTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.model.AddToTeamResult

class AddToTeamHandleImpl(private val messages: Messages) : CommandAddToTeamHandle<DiscordCommandContext> {

    override fun failUserNotFound(context: DiscordCommandContext, e: UserNotFoundException) {
        context.sendEmbed(messages.userNotFound(e.id))
    }

    override fun failUserAlreadyInTeam(context: DiscordCommandContext, e: UserAlreadyInTeamException) {
        context.sendEmbed(messages.userAlreadyInTeam(e.user))
    }

    override fun failTeamNotFound(context: DiscordCommandContext, e: TeamNotFoundException) {
        context.sendEmbed(messages.teamNotFound(e.teamId))
    }

    override fun onAdded(context: DiscordCommandContext, result: AddToTeamResult) {
        context.sendEmbed(messages.addedUserToTeam(result))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        context.sendEmbed(messages.commandUsage(usage))
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }
}
