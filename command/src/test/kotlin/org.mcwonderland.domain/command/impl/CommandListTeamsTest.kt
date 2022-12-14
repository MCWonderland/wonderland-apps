package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.commands.CommandListTeams
import org.mcwonderland.domain.commands.CommandListTeamsHandle
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team

internal class CommandListTeamsTest : CommandTestBase() {

    private lateinit var teamService: TeamService
    private lateinit var handle: CommandListTeamsHandle

    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandListTeams("listteams", teamService, handle)
    }

    @Test
    fun withoutPerm_shouldDenied() {
        executeCommand("listteams").also { verify { handle.failNoPermission() } }
    }

    @Test
    fun shouldResponseFromService() {
        val members = listOf(Dummies.createUserFullFilled())
        val teams = listOf(Team(members = members))

        sender.addAdminPerm()
        every { teamService.listTeams() } returns teams

        executeWithNoArgs().also { verify { handle.success(teams) } }
    }

}