package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandRemoveTeam(
    override val label: String,
    private val messenger: Messenger,
    private val userFinder: UserFinder,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String
        get() = "Usage: <user>"

    override fun execute(sender: PlatformUser, args: List<String>) {
        if (args.isEmpty()) {
            messenger.sendMessage(usage)
            return
        }

        val user = userFinder.findOrCreate(sender.id)
        val targetId = args[0]
        val teamAfterRemoveTarget = teamService.removeFromTeam(user, targetId)

        messenger.sendMessage(messages.userRemovedFromTeam(teamAfterRemoveTarget))
    }

}