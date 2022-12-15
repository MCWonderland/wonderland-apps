package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandListTeamsHandle
import org.mcwonderland.domain.model.Team

class CommandListTeamsHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandListTeamsHandle {

    override fun failNoPermission() {
        commandHistory.sendEmbed(messages.noPermission())
    }

    override fun success(teams: List<Team>) {
        commandHistory.sendEmbed(messages.listTeams(teams))
    }

}
