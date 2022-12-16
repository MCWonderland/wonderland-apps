package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.UserModification

class CommandRemoveTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandRemoveTeamHandle<CommandContext>
) : Command {

    override val usage: String = "/$label <user>"

    override fun execute(context: CommandContext) {
        val targetId = context.getArg(0) ?: return handle.failWithUsage(context, usage)

        return try {
            val teamAfterRemoveTarget = teamService.removeFromTeam(UserModification(context.sender, targetId))
            handle.onSuccess(teamAfterRemoveTarget)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(e)
        } catch (e: UserNotFoundException) {
            handle.failUserNotFound(e)
        } catch (e: UserNotInTeamException) {
            handle.failUserNotInTeam(e)
        }
    }

}

interface CommandRemoveTeamHandle<Context : CommandContext> : FailWithUsage<Context> {
    fun onSuccess(teamAfterRemoveTarget: Team)
    fun failPermissionDenied(e: PermissionDeniedException)
    fun failUserNotFound(e: UserNotFoundException)
    fun failUserNotInTeam(e: UserNotInTeamException)
}

