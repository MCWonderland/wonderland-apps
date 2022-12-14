package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.exceptions.UserNotInTeamException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.UserModification

internal class CommandRemoveTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandRemoveTeamHandle<CommandContext>


    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandRemoveTeam("removeTeam", teamService, handle)
    }

    @Test
    fun withoutArgs_showUsage() {
        executeWithNoArgs().also { verify { handle.failWithUsage(context, command.usage) } }
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(Dummies.createUserFullFilled())
        val expectTeam = Team(members = membersLeftAfterRemoved)

        every { teamService.removeFromTeam(UserModification(sender, "target")) } returns expectTeam

        executeCommand(listOf("target")).also {
            verify { handle.onSuccess(context, expectTeam) }
        }
    }

    @Test
    fun testExceptionMapping() {
        val target = Dummies.createUserFullFilled()

        assertExceptionMapping(PermissionDeniedException()) { handle.failPermissionDenied(context, it) }
        assertExceptionMapping(UserNotFoundException("target")) { handle.failUserNotFound(context, it) }
        assertExceptionMapping(UserNotInTeamException(target)) { handle.failUserNotInTeam(context, it) }
    }

    private fun <T : Exception> assertExceptionMapping(exception: T, block: (T) -> Unit) {
        every { teamService.removeFromTeam(UserModification(sender, "target")) } throws exception
        executeCommand("target")
        verify { block(exception) }
    }

}