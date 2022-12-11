package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.User

class CommandListTeams(
    override val label: String,
    private val teamService: TeamService,
    private val messages: Messages,
) : Command {
    override val usage: String = "/$label"

    override fun execute(sender: User, args: List<String>): CommandResponse {
        return try {
            sender.checkAdminPermission()

            val teams = this.teamService.listTeams()
            ok(messages.teamList(teams))
        } catch (e: PermissionDeniedException) {
            fail(messages.noPermission())
        }
    }

}