package org.mcwonderland.discord.commands

import org.mcwonderland.discord.config.Messages
import org.mcwonderland.discord.model.DiscordCommandContext
import org.mcwonderland.domain.commands.CommandDeleteTeamHandle

class DeleteTeamHandleImpl(
    private val messages: Messages,
) : CommandDeleteTeamHandle<DiscordCommandContext> {

    override fun success(context: DiscordCommandContext, teamId: String) {
        context.sendEmbed(messages.teamDeleted(teamId))
    }

    override fun failWithUsage(context: DiscordCommandContext, usage: String) {
        context.sendEmbed(messages.commandUsage(usage))
    }

}
