package org.mcwonderland.domain.commands

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.TeamService

class CommandClearTeamsTest : CommandTestBase() {

    private lateinit var handle: CommandClearTeamHandle<CommandContext>
    private lateinit var teamService: TeamService

    @BeforeEach
    fun setup() {
        handle = mockk(relaxed = true)
        teamService = mockk(relaxed = true)
        command = CommandClearTeams("clearteam", handle, teamService)
    }

    @Test
    fun basic() {
        assertUsageStartWithLabel()
    }

    @Test
    fun withoutPermission_shouldDenied() {
        executeWithNoArgs()
        verify { handle.failPermissionDenied(context, any()) }
    }

    @Test
    fun shouldCallService() {
        sender.addAdminPerm()
        every { teamService.clearTeams() } returns 10

        executeWithNoArgs()

        verify { handle.success(context, 10) }
    }

}