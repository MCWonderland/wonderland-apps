package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandRemoveTeam(
    override val label: String,
    private val messenger: Messenger,
    private val userFinder: UserFinder,
    private val teamService: TeamService
) : Command {

    override val usage: String
        get() = "Usage: <user>"

    override fun execute(sender: PlatformUser, args: List<String>) {
        if (args.isEmpty()) {
            messenger.sendMessage(usage)
            return
        }

        val user = userFinder.findOrCreate(sender.id)
        teamService.removeFromTeam(user, args[0])
    }

}