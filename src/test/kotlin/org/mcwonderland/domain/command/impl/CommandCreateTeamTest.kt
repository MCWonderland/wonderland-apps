package org.mcwonderland.domain.command.impl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.model.CommandSender

internal class CommandCreateTeamTest {

    private lateinit var messageSender: MessengerFake
    private lateinit var command: CommandCreateTeam
    private lateinit var sender: CommandSender

    @BeforeEach
    fun setup() {
        sender = CommandSender("sender")
        messageSender = MessengerFake()
        command = CommandCreateTeam("createTeam", messageSender)
    }

    @Test
    fun withoutArgs_shouldShowUsage() {
        command.execute(sender, listOf())

        assert(messageSender.lastMessage == command.usage)
    }

    @Test
    fun withoutPermission_shouldDenied() {


    }


}