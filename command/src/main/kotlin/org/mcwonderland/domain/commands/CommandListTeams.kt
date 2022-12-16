package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandListTeamsHandle<CommandContext>
) : Command {
    override val usage: String = "/$label"

    override fun execute(context: CommandContext) {
        return try {
            context.checkAdminPermission()

            val teams = this.teamService.listTeams()

            handle.success(context, teams)
        } catch (e: PermissionDeniedException) {
            handle.failNoPermission(context)
        }
    }

}

interface CommandListTeamsHandle<Context : CommandContext> {
    fun failNoPermission(context: Context)
    fun success(context: Context, teams: List<Team>)
}