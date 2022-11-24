package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandCreateTeam(
    override val label: String,
    private val messenger: Messenger,
    private val userFinder: UserFinder,
    private val teamService: TeamService
) : Command {

    override val usage: String = "Usage: /$label <id> <id>...."

    override fun execute(sender: PlatformUser, args: List<String>) {
        if (args.isEmpty()) {
            messenger.sendMessage(usage)
            return
        }

        try {
            val team = teamService.createTeam(userFinder.findOrCreate(sender.id), args)
            messenger.sendMessage("Team created with members: ${team.members.joinToString(", ")}")
        } catch (e: Exception) {
            messenger.sendMessage(e.message ?: "Unknown error")
        }
    }


}