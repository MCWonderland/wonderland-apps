package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

internal class CommandRemoveTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService


    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        command = CommandRemoveTeam("removeTeam", messenger, userFinder, teamService, messages)
    }

    @Test
    fun withoutArgs_showUsage() {
        executeWithNoArgs()

        assertEquals(command.usage, messenger.lastMessage)
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(User("member_left"))
        val expectTeam = Team(membersLeftAfterRemoved)

        executeCommand(listOf("target"))
        every { teamService.removeFromTeam(user, "target") } returns expectTeam

        assertEquals(messages.userRemovedFromTeam(expectTeam), messenger.lastMessage)
    }

}