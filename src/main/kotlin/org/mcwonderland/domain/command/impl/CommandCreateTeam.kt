package org.mcwonderland.domain.command.impl

import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandResponse
import org.mcwonderland.domain.command.CommandStatus
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser

class CommandCreateTeam(
    override val label: String,
    private val userFinder: UserFinder,
    private val teamService: TeamService,
    private val messages: Messages
) : Command {

    override val usage: String = "Usage: /$label <id> <id>...."

    override fun execute(sender: PlatformUser, args: List<String>): CommandResponse {
        if (args.isEmpty())
            return CommandResponse(CommandStatus.FAILURE, listOf(usage))

        return try {
            val team = teamService.createTeam(userFinder.findOrCreate(sender.id), args)
            CommandResponse(CommandStatus.SUCCESS, listOf(messages.teamCreated(team)))
        } catch (e: Exception) {
            CommandResponse(CommandStatus.FAILURE, listOf(e.message ?: "Error"))
        }
    }

}