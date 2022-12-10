package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.TeamService

class CommandDeleteTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        command = CommandDeleteTeam("deleteteam", teamService, messages)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        executeWithNoArgs().assertFail(command.usage)
    }

    @Test
    fun shouldCallService() {
        executeCommand("teamId")
            .assertSuccess(messages.teamDeleted("teamId"))
            .also {
                verify { teamService.deleteTeam(sender, "teamId") }
            }
    }

}