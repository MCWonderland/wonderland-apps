package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

internal class CommandRemoveTeamTest : CommandTestBase() {

    private lateinit var teamService: TeamService


    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        command = CommandRemoveTeam("removeTeam", teamService, messages)
    }

    @Test
    fun withoutArgs_showUsage() {
        executeWithNoArgs().assertFail(command.usage)
    }

    @Test
    fun shouldCallService() {
        val membersLeftAfterRemoved = listOf(User("member_left"))
        val expectTeam = Team(membersLeftAfterRemoved)

        every { teamService.removeFromTeam(sender, "target") } returns expectTeam

        executeCommand(listOf("target")).assertSuccess(messages.userRemovedFromTeam(expectTeam))
    }

}