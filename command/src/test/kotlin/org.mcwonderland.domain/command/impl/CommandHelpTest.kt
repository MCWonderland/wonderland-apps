package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.command.CommandStub
import org.mcwonderland.domain.commands.CommandHelp
import org.mcwonderland.domain.commands.CommandHelpHandle

class CommandHelpTest : CommandTestBase() {

    private lateinit var handle: CommandHelpHandle

    private val commands = listOf(
        CommandStub("cmdA"),
        CommandStub("cmdB"),
    )

    @BeforeEach
    fun setup() {
        handle = mockk(relaxed = true)
        command = CommandHelp("help", commands, handle)
    }

    @Test
    fun shouldSendHelpMessage() {
        executeWithNoArgs().also { verify { handle.showHelp(commands) } }
    }

}