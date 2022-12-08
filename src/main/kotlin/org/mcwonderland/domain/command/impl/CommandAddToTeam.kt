package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.UserModification

class CommandAddToTeam(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "Usage: /$label <team> <id>"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        val teamId = args.getOrNull(0) ?: return fail(usage)
        val userId = args.getOrNull(1) ?: return fail(usage)

        return try {
            val result = teamService.addUserToTeam(UserModification(sender, userId), teamId)
            ok(messages.addedUserToTeam(result))
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        } catch (e: UserNotFoundException) {
            fail(messages.userNotFound(e.id))
        } catch (e: UserAlreadyInTeamException) {
            fail(messages.userAlreadyInTeam(sender))
        } catch (e: TeamNotFoundException) {
            fail(messages.teamNotFound(e.teamId))
        }
    }

}