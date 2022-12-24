package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.features.TeamService

class CommandDeleteTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandDeleteTeamHandle<CommandContext>
) : Command {

    override val usage: String = "$label <teamId>"

    override fun execute(context: CommandContext) {
        val teamId = context.getArg(0) ?: return handle.failWithUsage(context, usage)
        teamService.deleteTeam(context.sender, teamId)
        return handle.success(context, teamId)
    }

}

interface CommandDeleteTeamHandle<Context : CommandContext> : FailWithUsage<Context> {
    fun success(context: Context, teamId: String)
}
