package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandListTeamsHandle
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>) {
        return try {
            sender.checkAdminPermission()

            val teams = this.teamService.listTeams()

            handle.success(teams)
        } catch (e: PermissionDeniedException) {
            handle.failNoPermission()
        }
    }

}

interface CommandListTeamsHandle {
    fun failNoPermission()
    fun success(teams: List<Team>)
}