package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.User

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages,
) : Command {

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val teams = this.teamService.listTeams(sender)

        return CommandResponse(CommandStatus.SUCCESS, listOf(messages.teamList(teams)))
    }

}