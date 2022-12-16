package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team

class CommandCreateTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandCreateTeamHandle
) : Command {

    override val usage: String = "/$label <id> <id>...."

    override fun execute(context: CommandContext) {
        val ids = context.args

        if (ids.isEmpty())
            return handle.failWithUsage(usage)

        return try {
            val team = teamService.createTeam(context.sender, ids)
            handle.success(team)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(e)
        } catch (e: MemberCantBeEmptyException) {
            handle.failMembersCantBeEmpty(e)
        } catch (e: UsersNotFoundException) {
            handle.failUsersNotFound(e)
        } catch (e: UsersAlreadyInTeamException) {
            handle.failUsersAlreadyInTeam(e)
        }
    }

}

interface CommandCreateTeamHandle : FailWithUsage, FailNoPermission {
    fun success(team: Team)
    fun failMembersCantBeEmpty(e: MemberCantBeEmptyException)
    fun failUsersNotFound(e: UsersNotFoundException)
    fun failUsersAlreadyInTeam(e: UsersAlreadyInTeamException)
}