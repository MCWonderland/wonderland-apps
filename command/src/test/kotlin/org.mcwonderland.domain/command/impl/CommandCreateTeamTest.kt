package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.commands.CommandCreateTeam
import org.mcwonderland.domain.model.Team

internal class CommandCreateTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    private val ids = listOf("id", "id2")
    private val team = Team(members = listOf(Dummies.createUserFullFilled()))

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
        assertExceptionMapping(UsersNotFoundException(listOf("1")), messages.membersCouldNotFound(listOf("1")))
        assertExceptionMapping(
            UsersAlreadyInTeamException(listOf(sender)),
            messages.membersAlreadyInTeam(listOf(sender))
        )
    }

    private fun assertExceptionMapping(exception: Exception, noPermission: String) {
        every { teamService.createTeam(any(), any()) } throws exception
        executeCommand(ids).assertFail(noPermission)
    }

    @Test
    fun success_shouldSendMessage() {
        every { teamService.createTeam(sender, ids) } returns team
        executeCommand(ids).assertSuccess(messages.teamCreated(team))
    }
}