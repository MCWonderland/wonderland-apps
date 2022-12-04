package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class CommandRemoveTeam(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String
        get() = "Usage: <user>"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        if (args.isEmpty())
            return CommandResponse(CommandStatus.FAILURE, listOf(usage))

        val targetId = args[0]
        val teamAfterRemoveTarget = teamService.removeFromTeam(sender, targetId)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.userRemovedFromTeam(teamAfterRemoveTarget)))
    }

}