package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User

class CommandCreateTeam(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "Usage: /$label <id> <id>...."

    override fun execute(sender: User, args: List<String>): CommandResponse {
        if (args.isEmpty())
            return failWithUsage()

        return try {
            val team = teamService.createTeam(sender, args)
            ok(messages.teamCreated(team))
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        } catch (e: MemberCantBeEmptyException) {
            fail(messages.membersCantBeEmpty())
        } catch (e: UsersNotFoundException) {
            fail(messages.membersCouldNotFound(e.ids))
        } catch (e: UsersAlreadyInTeamException) {
            fail(messages.membersAlreadyInTeam(e.users))
        }
    }

}