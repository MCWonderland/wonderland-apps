package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandAddToTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.model.AddToTeamResult

class AddToTeamHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandAddToTeamHandle<DiscordCommandContext> {

    override fun failUserNotFound(context: DiscordCommandContext, e: UserNotFoundException) {
        commandHistory.sendEmbed(messages.userNotFound(e.id))
    }

    override fun failUserAlreadyInTeam(context: DiscordCommandContext, e: UserAlreadyInTeamException) {
        commandHistory.sendEmbed(messages.userAlreadyInTeam(e.user))
    }

    override fun failTeamNotFound(e: TeamNotFoundException) {
        commandHistory.sendEmbed(messages.teamNotFound(e.teamId))
    }

    override fun onAdded(result: AddToTeamResult) {
        commandHistory.sendEmbed(messages.addedUserToTeam(result))
    }

    override fun failWithUsage(usage: String) {
        commandHistory.sendEmbed(messages.commandUsage(usage))
    }

    override fun failPermissionDenied(e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        TODO("Not yet implemented")
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        TODO("Not yet implemented")
    }

    override fun failTeamNotFound(context: DiscordCommandContext, e: TeamNotFoundException) {
        TODO("Not yet implemented")
    }

    override fun onAdded(context: DiscordCommandContext, result: AddToTeamResult) {
        TODO("Not yet implemented")
    }

}