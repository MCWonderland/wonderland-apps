package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService

class CommandClearTeams(
    override val label: String,
    private val handle: CommandClearTeamHandle<CommandContext>,
    private val teamService: TeamService
) : Command {
    override val usage: String = label

    override fun execute(context: CommandContext) {
        try {
            context.checkAdminPermission()
            teamService.clearTeams().let {
                handle.success(context, it)
            }
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(context, e)
        }
    }
}

interface CommandClearTeamHandle<Context : CommandContext> : FailNoPermission<Context> {
    fun success(context: Context, clearAmount: Int)

}