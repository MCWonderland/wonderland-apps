package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.PlatformUser
import org.mcwonderland.domain.model.Team
import org.mcwonderland.domain.model.User

internal class CommandListTeamsTest {

    private lateinit var command: CommandListTeams
    private lateinit var teamService: TeamService
    private lateinit var messenger: MessengerFake
    private lateinit var messages: Messages

    @BeforeEach
    fun setUp() {
        teamService = mockk(relaxed = true)
        messenger = MessengerFake()
        messages = MessagesStub()
        command = CommandListTeams("listteams", teamService, messages, messenger)
    }


    @Test
    fun shouldResponseFromService() {
        val sender = PlatformUser("sender")
        val members = listOf(User("member"))
        val teams = listOf(Team(members))

        every { teamService.listTeams() } returns teams

        command.execute(sender, emptyList())

        assertEquals(messages.teamList(teams), messenger.lastMessage)
    }


}