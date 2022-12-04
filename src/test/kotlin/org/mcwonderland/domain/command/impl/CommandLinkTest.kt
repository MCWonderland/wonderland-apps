package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.features.AccountLinker
import kotlin.test.Test

class CommandLinkTest : CommandTestBase() {
    private val label = "link"
    private lateinit var accountLinker: AccountLinker

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        command = CommandLink(label, accountLinker, messages)
    }

    @Test
    fun missingArguments() {
        executeWithNoArgs().assertFail(messages.invalidArg("mcIgn"))
    }

    @Nested
    inner class Executed {

        private val targetId: String = "target_id"

        @Test
        fun linked_sendMessage() {
            every { accountLinker.link(sender, targetId) } returns sender

            executeCommand(targetId).assertSuccess(messages.linked(sender))
        }
    }
}
