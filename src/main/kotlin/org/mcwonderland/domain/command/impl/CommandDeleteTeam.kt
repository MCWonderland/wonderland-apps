package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User

class CommandDeleteTeam(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "Usage: /$label <teamId>"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val teamId = args.firstOrNull() ?: return failWithUsage()
        teamService.deleteTeam(sender, teamId)
        return ok(messages.teamDeleted(teamId))
    }

}