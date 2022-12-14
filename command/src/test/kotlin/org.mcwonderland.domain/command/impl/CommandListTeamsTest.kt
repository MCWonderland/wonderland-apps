package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.commands.CommandListTeams
import org.mcwonderland.domain.model.Team

internal class CommandListTeamsTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        command = CommandListTeams("listteams", teamService, messages)
    }

    @Test
    fun withoutPerm_shouldDenied() {
        executeCommand("listteams").assertFail(messages.noPermission())
    }

    @Test
    fun shouldResponseFromService() {
        val members = listOf(Dummies.createUserFullFilled())
        val teams = listOf(Team(members = members))

        sender.addAdminPerm()
        every { teamService.listTeams() } returns teams

        executeWithNoArgs().assertSuccess(messages.teamList(teams))
    }

    @Test
    fun testExceptionMapping() {
        every { teamService.listTeams() } throws PermissionDeniedException()

        executeWithNoArgs().assertFail(messages.noPermission())
    }



}