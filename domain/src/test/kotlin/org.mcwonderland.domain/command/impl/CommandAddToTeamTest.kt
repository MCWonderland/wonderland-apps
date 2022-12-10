package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
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

    @Test
    fun testExceptionMapping() {
        assertExceptionMapping(PermissionDeniedException(), messages.noPermission())
        assertExceptionMapping(UserNotFoundException("id"), messages.userNotFound("id"))
        assertExceptionMapping(UserAlreadyInTeamException(sender), messages.userAlreadyInTeam(sender))
        assertExceptionMapping(TeamNotFoundException("id"), messages.teamNotFound("id"))
    }


    private fun assertExceptionMapping(exception: Exception, noPermission: String) {
        every { teamService.addUserToTeam(any(), any()) } throws exception
        executeCommand("teamId id1").assertFail(noPermission)
    }

}