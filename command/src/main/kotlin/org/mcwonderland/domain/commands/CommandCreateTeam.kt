package org.mcwonderland.domain.commands

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.handles.FailNoPermission
import org.mcwonderland.domain.command.handles.FailWithUsage
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

class CommandCreateTeam(
    override val label: String,
    private val teamService: TeamService,
    private val handle: CommandCreateTeamHandle
) : Command {

    override val usage: String = "/$label <id> <id>...."

    override fun execute(sender: User, args: List<String>) {
        if (args.isEmpty())
            return handle.failWithUsage(usage)

        return try {
            val team = teamService.createTeam(sender, args)
            handle.success(team)
        } catch (e: PermissionDeniedException) {
            handle.failNoPermission(e)
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
    fun failNoPermission(e: PermissionDeniedException)
    fun failMembersCantBeEmpty(e: MemberCantBeEmptyException)
    fun failUsersNotFound(e: UsersNotFoundException)
    fun failUsersAlreadyInTeam(e: UsersAlreadyInTeamException)
}