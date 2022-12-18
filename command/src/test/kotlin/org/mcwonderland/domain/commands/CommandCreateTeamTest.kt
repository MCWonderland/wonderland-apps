package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.MemberCantBeEmptyException
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.exceptions.UsersAlreadyInTeamException
import org.mcwonderland.domain.exceptions.UsersNotFoundException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.commands.CommandCreateTeam
import org.mcwonderland.domain.commands.CommandCreateTeamHandle
import org.mcwonderland.domain.model.Team

internal class CommandCreateTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandCreateTeamHandle<CommandContext>

    private val ids = listOf("id", "id2")
    private val team = Team(members = listOf(Dummies.createUserFullFilled()))

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandCreateTeam("createTeam", teamService, handle)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        executeWithNoArgs().also {
            verify { handle.failWithUsage(context, command.usage) }
        }
    }

    @Test
    fun testExceptionMessageMappings() {
        assertExceptionMapping(PermissionDeniedException()) { handle.failPermissionDenied(context, it) }
        assertExceptionMapping(MemberCantBeEmptyException()) { handle.failMembersCantBeEmpty(context, it) }
        assertExceptionMapping(UsersNotFoundException(listOf("1"))) { handle.failUsersNotFound(context, it) }
        assertExceptionMapping(UsersAlreadyInTeamException(listOf(sender))) {
            handle.failUsersAlreadyInTeam(context, it)
        }
    }

    private fun <T : Exception> assertExceptionMapping(ex: T, function: (ex: T) -> Unit) {
        every { teamService.createTeam(any(), any()) } throws ex
        executeCommand(ids)
        verify { function(ex) }
    }

    @Test
    fun success_shouldSendMessage() {
        every { teamService.createTeam(sender, ids) } returns team
        executeCommand(ids).also {
            verify { handle.success(context, team) }
        }
    }
}