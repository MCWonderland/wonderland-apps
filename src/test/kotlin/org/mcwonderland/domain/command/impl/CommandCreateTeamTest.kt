package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team

internal class CommandCreateTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        command = CommandCreateTeam("createTeam", teamService, messages)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        executeWithNoArgs().assertFail(command.usage)
    }

    @Test
    fun testExceptionMessageMappings() {
        assertExceptionMapping(PermissionDeniedException(), messages.noPermission())
        assertExceptionMapping(MemberCantBeEmptyException(), messages.membersCantBeEmpty())
        assertExceptionMapping(UserNotFoundException(listOf("1")), messages.membersCouldNotFound(listOf("1")))
        assertExceptionMapping(
            UsersAlreadyInTeamException(listOf(sender)),
            messages.membersAlreadyInTeam(listOf(sender))
        )
    }

    private fun assertExceptionMapping(exception: Exception, noPermission: String) {
        every { teamService.createTeam(any(), any()) } throws exception
        executeWithNoArgs().assertFail(noPermission)
    }

    @Test
    fun success_shouldSendMessage() {
        val ids = listOf("id", "id2")
        val team = Team(
            members = listOf(Dummies.createUserFullFilled())
        )

        every { teamService.createTeam(sender, ids) } returns team

        executeCommand(ids).assertSuccess(messages.teamCreated(team))
    }
}