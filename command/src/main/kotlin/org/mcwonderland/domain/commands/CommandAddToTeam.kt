package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.UserModification

class CommandAddToTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandAddToTeamHandle<CommandContext>,
) : Command {

    override val usage: String = "/$label <team> <id>"

    override fun execute(context: CommandContext) {
        val teamId = context.getArg(0) ?: return handle.failWithUsage(context, usage)
        val userId = context.getArg(1) ?: return handle.failWithUsage(context, usage)

        try {
            val result = teamService.addUserToTeam(UserModification(context.sender, userId), teamId)
            handle.onAdded(context, result)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(context, e)
        } catch (e: UserNotFoundException) {
            handle.failUserNotFound(context, e)
        } catch (e: UserAlreadyInTeamException) {
            handle.failUserAlreadyInTeam(context, e)
        } catch (e: TeamNotFoundException) {
            handle.failTeamNotFound(context, e)
        }
    }
}

interface CommandAddToTeamHandle<Context : CommandContext> : FailWithUsage<Context>, FailNoPermission<Context> {
    fun failUserNotFound(context: Context, e: UserNotFoundException)
    fun failUserAlreadyInTeam(context: Context, e: UserAlreadyInTeamException)
    fun failTeamNotFound(context: Context, e: TeamNotFoundException)
    fun onAdded(context: Context, result: AddToTeamResult)
}