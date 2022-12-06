package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
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

        @Test
        fun testExceptionMappings() {
            assertException(AccountAlreadyLinkedException(linkedId = "mcId"), messages.accountAlreadyLinked("mcId"))
            assertException(MCAccountNotFoundException(searchStr = "id"), messages.mcAccountWithIgnNotFound("id"))
            assertException(MCAccountLinkedByOthersException(ign = "ign"), messages.targetAccountAlreadyLink("ign"))
        }


        private fun assertException(exception: Exception, message: String) {
            every { accountLinker.link(sender, targetId) } throws exception
            executeCommand(targetId).assertFail(message)
        }
    }
}
