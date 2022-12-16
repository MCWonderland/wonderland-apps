package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandContextStub
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.TeamNotFoundException
import org.mcwonderland.domain.exceptions.UserAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UserNotFoundException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.commands.CommandAddToTeam
import org.mcwonderland.domain.commands.CommandAddToTeamHandle
import org.mcwonderland.domain.model.AddToTeamResult
import org.mcwonderland.domain.model.UserModification

class CommandAddToTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandAddToTeamHandle<CommandContext>

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandAddToTeam("addtoteam", teamService, handle)
    }

    @Test
    fun missingArgs_shouldError() {
        executeWithNoArgs().also { assertFailWithUsage() }
        executeCommand("team").also { assertFailWithUsage() }
    }

    private fun assertFailWithUsage() {
        verify { handle.failWithUsage(context, command.usage) }
    }

    @Test
    fun shouldCallService() {
        val result = AddToTeamResult(
            user = Dummies.createUserFullFilled(),
            team = Dummies.createTeam()
        )

        every { teamService.addUserToTeam(UserModification(sender, "targetId"), "teamId") } returns result

        executeCommand("teamId targetId").also { verify { handle.onAdded(context, result) } }
    }

    @Test
    fun testExceptionMapping() {
        assertExceptionMapping(PermissionDeniedException()) { handle.failPermissionDenied(context, it) }
        assertExceptionMapping(UserNotFoundException("id")) { handle.failUserNotFound(context, it) }
        assertExceptionMapping(UserAlreadyInTeamException(sender)) { handle.failUserAlreadyInTeam(context, it) }
        assertExceptionMapping(TeamNotFoundException("id")) { handle.failTeamNotFound(context, it) }
    }

    private fun <T : Exception> assertExceptionMapping(ex: T, function: (ex: T) -> Unit) {
        every { teamService.addUserToTeam(UserModification(sender, "id1"), "teamId") } throws ex
        executeCommand("teamId id1")
        verify { function(ex) }
    }

}
