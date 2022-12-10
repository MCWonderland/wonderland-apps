package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.PermissionDeniedException
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

internal class CommandListTeamsTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        command = CommandListTeams("listteams", teamService, messages)
    }


    @Test
    fun shouldResponseFromService() {
        val members = listOf(User("member"))
        val teams = listOf(Team(members = members))

        every { teamService.listTeams(sender) } returns teams

        executeWithNoArgs().assertSuccess(messages.teamList(teams))
    }

    @Test
    fun testExceptionMapping() {
        every { teamService.listTeams(sender) } throws PermissionDeniedException()

        executeWithNoArgs().assertFail(messages.noPermission())
    }


}