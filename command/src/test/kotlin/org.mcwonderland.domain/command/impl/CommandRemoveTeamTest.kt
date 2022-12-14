package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.commands.CommandRemoveTeam
import org.mcwonderland.domain.commands.CommandRemoveTeamHandle
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.UserModification

internal class CommandRemoveTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandRemoveTeamHandle


    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandRemoveTeam("removeTeam", teamService, handle)
    }

    @Test
    fun withoutArgs_showUsage() {
        executeWithNoArgs().also { verify { handle.failWithUsage(command.usage) } }
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(Dummies.createUserFullFilled())
        val expectTeam = Team(members = membersLeftAfterRemoved)

        every { teamService.removeFromTeam(UserModification(sender, "target")) } returns expectTeam

        executeCommand(listOf("target")).also {
            verify { handle.onSuccess(expectTeam) }
        }
    }

    @Test
    fun testExceptionMapping() {
        val target = Dummies.createUserFullFilled()

        assertExceptionMapping(PermissionDeniedException()) { handle.failPermissionDenied(it) }
        assertExceptionMapping(UserNotFoundException("target")) { handle.failUserNotFound(it) }
        assertExceptionMapping(UserNotInTeamException(target)) { handle.failUserNotInTeam(it) }
    }

    private fun <T : Exception> assertExceptionMapping(exception: T, block: (T) -> Unit) {
        every { teamService.removeFromTeam(UserModification(sender, "target")) } throws exception
        executeCommand("target")
        verify { block(exception) }
    }

}