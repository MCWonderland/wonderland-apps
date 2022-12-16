package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
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
) : CommandCreateTeamHandle<DiscordCommandContext> {

    override fun success(context: DiscordCommandContext, team: Team) {
        commandHistory.sendEmbed(messages.teamCreated(team))
    }

    override fun failMembersCantBeEmpty(context: DiscordCommandContext, e: MemberCantBeEmptyException) {
        commandHistory.sendEmbed(messages.membersCantBeEmpty())
    }

    override fun failUsersNotFound(context: DiscordCommandContext, e: UsersNotFoundException) {
        commandHistory.sendEmbed(messages.membersCouldNotFound(e.ids))
    }

    override fun failUsersAlreadyInTeam(context: DiscordCommandContext, e: UsersAlreadyInTeamException) {
        commandHistory.sendEmbed(messages.membersAlreadyInTeam(e.users))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        commandHistory.sendEmbed(messages.commandUsage(usage))
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        commandHistory.sendEmbed(messages.noPermission())
    }

}
