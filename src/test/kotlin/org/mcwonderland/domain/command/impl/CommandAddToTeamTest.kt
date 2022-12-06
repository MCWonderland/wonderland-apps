package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.UserModification

class CommandAddToTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        command = CommandAddToTeam("addtoteam", teamService, messages)
    }

    @Test
    fun missingArgs_shouldError() {
        executeWithNoArgs().assertFail(command.usage)
        executeCommand("team").assertFail(command.usage)
    }

    @Test
    fun shouldCallService() {
        val result = AddToTeamResult(
            user = Dummies.createUserFullFilled(),
            team = Dummies.createTeam()
        )

        every { teamService.addUserToTeam(UserModification(sender, "targetId"), "teamId") } returns result

        executeCommand("teamId id1").assertSuccess(messages.addedUserToTeam(result))
    }

}