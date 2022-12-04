package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
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
        command = CommandLink(label, accountLinker, userFinder, messages)
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
            every { accountLinker.link(user, targetId) } returns user

            executeCommand(targetId).assertSuccess(messages.linked(user))
        }
    }
}
