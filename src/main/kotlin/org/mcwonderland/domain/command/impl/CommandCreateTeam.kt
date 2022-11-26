package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandCreateTeam(
    override val label: String,
    private val messenger: Messenger,
    private val userFinder: UserFinder,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "Usage: /$label <id> <id>...."

    override fun execute(sender: PlatformUser, args: List<String>) = runCommand(messenger) {
        if (args.isEmpty()) {
            messenger.sendMessage(usage)
            return@runCommand
        }

        val team = teamService.createTeam(userFinder.findOrCreate(sender.id), args)
        messenger.sendMessage(messages.teamCreated(team))
    }


}