package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.UserModification

class CommandRemoveTeam(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "/$label <user>"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        if (args.isEmpty())
            return failWithUsage()

        val targetId = args[0]


        return try {
            val teamAfterRemoveTarget = teamService.removeFromTeam(UserModification(sender, targetId))
            ok(messages.userRemovedFromTeam(teamAfterRemoveTarget))
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        } catch (e: UserNotFoundException) {
            fail(messages.userNotFound(targetId))
        } catch (e: UserNotInTeamException) {
            fail(messages.userNotInTeam(e.user))
        }
    }

}