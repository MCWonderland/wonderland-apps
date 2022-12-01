package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages,
    private val messenger: Messenger,
    private val userFinder: UserFinder
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) = runCommand(messenger) {
        val user = userFinder.findOrCreate(sender.id)
        val teams = this.teamService.listTeams(user)

        this.messenger.sendMessage(messages.teamList(teams))
    }

}