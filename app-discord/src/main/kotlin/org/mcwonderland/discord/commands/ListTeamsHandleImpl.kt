package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandListTeamsHandle
import org.mcwonderland.domain.model.Team

class ListTeamsHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandListTeamsHandle<DiscordCommandContext> {

    override fun failNoPermission(context: DiscordCommandContext) {
        commandHistory.sendEmbed(messages.noPermission())
    }

    override fun success(context: DiscordCommandContext, teams: List<Team>) {
        commandHistory.sendEmbed(messages.listTeams(teams))
    }

}
