package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

internal class CommandRemoveTeamTest {

    private lateinit var command: Command
    private lateinit var messenger: MessengerFake
    private lateinit var userFinder: UserFinder
    private lateinit var teamService: TeamService
    private lateinit var messages: Messages

    private lateinit var user: User
    private val sender = PlatformUser("sender")

    @BeforeEach
    fun setUp() {
        user = User()

        teamService = mockk(relaxed = true)
        messenger = MessengerFake()
        messages = Messages()
        userFinder = UserFinderStub(user)
        command = CommandRemoveTeam("removeTeam", messenger, userFinder, teamService)
    }

    @Test
    fun withoutArgs_showUsage() {
        command.execute(sender, emptyList())

        assertEquals(command.usage, messenger.lastMessage)
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(User("member_left"))
        val expectTeam = Team(membersLeftAfterRemoved)

        command.execute(sender, listOf("target"))
        every { teamService.removeFromTeam(user, "target") } returns expectTeam


    }

}