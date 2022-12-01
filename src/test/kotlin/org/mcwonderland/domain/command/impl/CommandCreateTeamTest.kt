package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import kotlin.test.assertEquals

internal class CommandCreateTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService

    @BeforeEach
    fun setup() {
        teamService = mockk(relaxed = true)
        command = CommandCreateTeam("createTeam", messenger, userFinder, teamService, messages)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        executeWithNoArgs()
        assertEquals(messenger.lastMessage, command.usage)
    }

    @Test
    fun onException_shouldSendMessage() {
        val ids = listOf("id", "id2")
        every { teamService.createTeam(user, ids) } throws RuntimeException("Error")

        executeCommand(ids)

        assertEquals(messenger.lastMessage, "Error")
    }

    @Test
    fun success_shouldSendMessage() {
        val ids = listOf("id", "id2")
        val team = Team(
            members = listOf(Dummies.createUserFullFilled())
        )

        every { teamService.createTeam(user, ids) } returns team

        executeCommand(ids)

        assertEquals(messenger.lastMessage, messages.teamCreated(team))
    }
}