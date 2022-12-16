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

class CommandCreateTeam<T : CommandContext>(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandCreateTeamHandle<T>
) : Command<T> {

    override val usage: String = "/$label <id> <id>...."

    override fun execute(context: T) {
        val ids = context.args

        if (ids.isEmpty())
            return handle.failWithUsage(context, usage)

        return try {
            val team = teamService.createTeam(context.sender, ids)
            handle.success(context, team)
        } catch (e: PermissionDeniedException) {
            handle.failPermissionDenied(context, e)
        } catch (e: MemberCantBeEmptyException) {
            handle.failMembersCantBeEmpty(context, e)
        } catch (e: UsersNotFoundException) {
            handle.failUsersNotFound(context, e)
        } catch (e: UsersAlreadyInTeamException) {
            handle.failUsersAlreadyInTeam(context, e)
        }
    }

}

interface CommandCreateTeamHandle<Context : CommandContext> : FailWithUsage<Context>, FailNoPermission<Context> {
    fun success(context: Context, team: Team)
    fun failMembersCantBeEmpty(context: Context, e: MemberCantBeEmptyException)
    fun failUsersNotFound(context: Context, e: UsersNotFoundException)
    fun failUsersAlreadyInTeam(context: Context, e: UsersAlreadyInTeamException)
}