package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandListTeamsHandle
import org.mcwonderland.domain.model.Team

class ListTeamsHandleImpl(
    private val messages: Messages,
) : CommandListTeamsHandle<DiscordCommandContext> {

    override fun failNoPermission(context: DiscordCommandContext) {
        context.sendEmbed(messages.noPermission())
    }

    override fun success(context: DiscordCommandContext, teams: List<Team>) {
        context.sendEmbed(messages.listTeams(teams))
    }

}
