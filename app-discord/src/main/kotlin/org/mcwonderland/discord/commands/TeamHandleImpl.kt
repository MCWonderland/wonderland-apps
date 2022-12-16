package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandCreateTeamHandle
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.model.Team

class TeamHandleImpl(private val messages: Messages) : CommandCreateTeamHandle<DiscordCommandContext> {

    override fun success(context: DiscordCommandContext, team: Team) {
        context.sendEmbed(messages.teamCreated(team))
    }

    override fun failMembersCantBeEmpty(context: DiscordCommandContext, e: MemberCantBeEmptyException) {
        context.sendEmbed(messages.membersCantBeEmpty())
    }

    override fun failUsersNotFound(context: DiscordCommandContext, e: UsersNotFoundException) {
        context.sendEmbed(messages.membersCouldNotFound(e.ids))
    }

    override fun failUsersAlreadyInTeam(context: DiscordCommandContext, e: UsersAlreadyInTeamException) {
        context.sendEmbed(messages.membersAlreadyInTeam(e.users))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        context.sendEmbed(messages.commandUsage(usage))
    }

    override fun failPermissionDenied(context: DiscordCommandContext, e: PermissionDeniedException) {
        context.sendEmbed(messages.noPermission())
    }

}
