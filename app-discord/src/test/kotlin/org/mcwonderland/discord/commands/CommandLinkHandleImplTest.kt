package org.mcwonderland.discord.commands

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.discord.module.CommandHistory
import org.mcwonderland.discord.module.CommandHistoryStub
import org.mcwonderland.discord.module.CommandRecord
import org.mcwonderland.domain.fakes.Dummies

class CommandLinkHandleImplTest {

    private lateinit var handle: CommandLinkHandleImpl

    private lateinit var messages: Messages
    private lateinit var commandHistory: CommandHistory

    private val record = CommandRecord(
        channel = mockk(relaxed = true),
        user = Dummies.createUserFullFilled()
    )

    @BeforeEach
    fun setUp() {
        messages = MessagesStub()
        commandHistory = CommandHistoryStub(record)
        handle = CommandLinkHandleImpl(messages, commandHistory)
    }

    @Test
    fun missingArgId() {
        handle.missingArgId()

        verify { record.sendEmbed(messages.missingArg("mcIgn")) }
    }

    @Test
    fun linked() {
    }

    @Test
    fun failAccountAlreadyLinked() {
    }

    @Test
    fun failMcAccountNotFound() {
    }

    @Test
    fun failMcAccountLinkedByOthers() {
    }
}