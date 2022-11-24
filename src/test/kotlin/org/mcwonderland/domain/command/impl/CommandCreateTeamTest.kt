package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.Team
import kotlin.test.assertEquals

internal class CommandCreateTeamTest {

    private lateinit var command: CommandCreateTeam
    private lateinit var messageSender: MessengerFake
    private lateinit var teamService: TeamService
    private lateinit var userFinder: UserFinder
    private lateinit var sender: PlatformUser

    private val user = Dummies.createUserFullFilled()

    @BeforeEach
    fun setup() {
        sender = PlatformUser("id")
        messageSender = MessengerFake()
        userFinder = UserFinderStub(user)
        teamService = mockk(relaxed = true)
        command = CommandCreateTeam("createTeam", messageSender, userFinder, teamService)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        command.execute(sender, listOf())

        assertEquals(messageSender.lastMessage, command.usage)
    }

    @Test
    fun onException_shouldSendMessage() {
        val ids = listOf("id", "id2")
        every { teamService.createTeam(user, ids) } throws RuntimeException("Error")

        command.execute(sender, ids)

        assertEquals(messageSender.lastMessage, "Error")
    }

    @Test
    fun success_shouldSendMessage() {
        val ids = listOf("id", "id2")
        val team = Team(
            id = "teamId",
            members = listOf(Dummies.createUserFullFilled())
        )

        every { teamService.createTeam(user, ids) } returns team

        command.execute(sender, ids)

        assertEquals(messageSender.lastMessage, "Team created with members: ${team.members.joinToString(", ")}")
    }
}