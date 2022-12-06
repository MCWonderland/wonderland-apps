package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.model.UserModification

internal class CommandRemoveTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService


    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        command = CommandRemoveTeam("removeTeam", teamService, messages)
    }

    @Test
    fun withoutArgs_showUsage() {
        executeWithNoArgs().assertFail(command.usage)
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(User("member_left"))
        val expectTeam = Team(members = membersLeftAfterRemoved)

        every { teamService.removeFromTeam(UserModification(sender, "target")) } returns expectTeam

        executeCommand(listOf("target")).assertSuccess(messages.userRemovedFromTeam(expectTeam))
    }

    @Test
    fun testExceptionMapping() {
        val target = User()

        assertExceptionMapping(PermissionDeniedException(), messages.noPermission())
        assertExceptionMapping(UserNotFoundException("target"), messages.userNotFound("target"))
        assertExceptionMapping(UserNotInTeamException(target), messages.userNotInTeam(target))
    }

    private fun assertExceptionMapping(exception: Exception, message: String) {
        every { teamService.removeFromTeam(UserModification(sender, "target")) } throws exception
        executeCommand(listOf("target")).assertFail(message)
    }
}