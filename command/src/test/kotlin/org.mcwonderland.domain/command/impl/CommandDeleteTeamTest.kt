package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.commands.CommandDeleteTeam
import org.mcwonderland.domain.commands.CommandDeleteTeamHandle

class CommandDeleteTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandDeleteTeamHandle<CommandContext>

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandDeleteTeam("deleteteam", teamService, handle)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        executeWithNoArgs().also { verify { handle.failWithUsage(context, command.usage) } }
    }

    @Test
    fun shouldCallService() {
        executeCommand("teamId")
            .also {
                verify { teamService.deleteTeam(sender, "teamId") }
                verify { handle.success(context, "teamId") }
            }
    }

}