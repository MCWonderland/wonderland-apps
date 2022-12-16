package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.domain.commands.CommandDeleteTeamHandle

class DeleteTeamHandleImpl(
    private val messages: Messages,
    private val commandHistory: CommandHistory
) : CommandDeleteTeamHandle<DiscordCommandContext> {

    override fun success(context: DiscordCommandContext, teamId: String) {
        commandHistory.sendEmbed(messages.teamDeleted(teamId))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        commandHistory.sendEmbed(messages.commandUsage(usage))
    }

}
