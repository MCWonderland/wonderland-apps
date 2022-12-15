package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandCreateTeamHandle
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.model.Team

class TeamHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandCreateTeamHandle {

    override fun success(team: Team) {
        commandHistory.sendEmbed(messages.teamCreated(team))
    }

    override fun failMembersCantBeEmpty(e: MemberCantBeEmptyException) {
        commandHistory.sendEmbed(messages.membersCantBeEmpty())
    }

    override fun failUsersNotFound(e: UsersNotFoundException) {
        commandHistory.sendEmbed(messages.membersCouldNotFound(e.ids))
    }

    override fun failUsersAlreadyInTeam(e: UsersAlreadyInTeamException) {
        commandHistory.sendEmbed(messages.membersAlreadyInTeam(e.users))
    }

    override fun failWithUsage(usage: String) {
        commandHistory.sendEmbed(messages.commandUsage(usage))
    }

    override fun failPermissionDenied(e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

}
