package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.UserModification

class CommandAddToTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandAddToTeamHandle,
) : Command {

    override val usage: String = "/$label <team> <id>"

    override fun execute(sender: User, args: List<String>) {
        val teamId = args.getOrNull(0) ?: return handle.failWithUsage(usage)
        val userId = args.getOrNull(1) ?: return handle.failWithUsage(usage)

        try {
            val result = teamService.addUserToTeam(UserModification(sender, userId), teamId)
            handle.onAdded(result)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(e)
        } catch (e: UserNotFoundException) {
            handle.failUserNotFound(e)
        } catch (e: UserAlreadyInTeamException) {
            handle.failUserAlreadyInTeam(e)
        } catch (e: TeamNotFoundException) {
            handle.failTeamNotFound(e)
        }
    }
}

interface CommandAddToTeamHandle : FailWithUsage, FailNoPermission {
    fun failUserNotFound(e: UserNotFoundException)
    fun failUserAlreadyInTeam(e: UserAlreadyInTeamException)
    fun failTeamNotFound(e: TeamNotFoundException)
    fun onAdded(result: AddToTeamResult)
}