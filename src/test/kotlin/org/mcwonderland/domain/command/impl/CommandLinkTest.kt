package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.AccountLinker
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandLinkTest : CommandTestBase() {
    private val label = "link"
    private lateinit var accountLinker: AccountLinker

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        command = CommandLink(label, accountLinker, userFinder, messenger, messages)
    }

    @Test
    fun missingArguments() {
        executeWithNoArgs()
        assertEquals(messages.invalidArg("mcIgn"), messenger.lastMessage)
    }

    @Nested
    inner class Executed {

        private val targetId: String = "target_id"

        @Test
        fun linked_sendMessage() {
            executeCommand(targetId)

            every { accountLinker.link(user, targetId) } returns user

            assertEquals(messages.linked(user), messenger.lastMessage)
        }
    }
}
