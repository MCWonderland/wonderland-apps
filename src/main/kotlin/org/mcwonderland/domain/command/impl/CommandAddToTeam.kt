package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
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

        val result = teamService.addUsersToTeam(UserModification(sender, userId), teamId)

        return ok(messages.addedUserToTeam(result))
    }

}