package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User

class CommandDeleteTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandDeleteTeamHandle
) : Command {

    override val usage: String = "/$label <teamId>"

    override fun execute(sender: User, args: List<String>) {
        val teamId = args.firstOrNull() ?: return handle.failWithUsage(usage)
        teamService.deleteTeam(sender, teamId)
        return handle.success(teamId)
    }

}

interface CommandDeleteTeamHandle : FailWithUsage {
    fun success(teamId: String)
}
