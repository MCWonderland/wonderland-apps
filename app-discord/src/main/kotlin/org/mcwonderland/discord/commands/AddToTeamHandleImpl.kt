package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
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
) : CommandAddToTeamHandle {

    override fun failUserNotFound(e: UserNotFoundException) {
        commandHistory.sendEmbed(messages.userNotFound(e.id))
    }

    override fun failUserAlreadyInTeam(e: UserAlreadyInTeamException) {
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

}
