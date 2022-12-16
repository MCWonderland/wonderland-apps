package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.command.CommandDummy
import org.mcwonderland.domain.commands.CommandHelp
import org.mcwonderland.domain.commands.CommandHelpHandle

class CommandHelpTest : CommandTestBase() {

    private lateinit var handle: CommandHelpHandle<CommandContext>

    private val commands = listOf(
        CommandDummy("cmdA"),
        CommandDummy("cmdB"),
    )

    @BeforeEach
    fun setup() {
        handle = mockk(relaxed = true)
        command = CommandHelp("help", commands, handle)
    }

    @Test
    fun shouldSendHelpMessage() {
        executeWithNoArgs().also { verify { handle.showHelp(context, commands) } }
    }

}