package org.mcwonderland.domain.command.impl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.command.CommandStub
import org.mcwonderland.domain.commands.CommandHelp

class CommandHelpTest : CommandTestBase() {

    private val commands = listOf(
        CommandStub("cmdA"),
        CommandStub("cmdB"),
    )

    @BeforeEach
    fun setup() {
        command = CommandHelp("help", commands, messages)
    }

    @Test
    fun shouldSendHelpMessage() {
        executeWithNoArgs().assertSuccess(messages.commandHelp(commands))
    }

}