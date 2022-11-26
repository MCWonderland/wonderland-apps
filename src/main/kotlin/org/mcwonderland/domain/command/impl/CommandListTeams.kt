package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.PlatformUser

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages,
    private val messenger: Messenger
) : Command {

    override fun execute(sender: PlatformUser, args: List<String>) {
        val teams = this.teamService.listTeams()
        this.messenger.sendMessage(messages.teamList(teams))
    }

}