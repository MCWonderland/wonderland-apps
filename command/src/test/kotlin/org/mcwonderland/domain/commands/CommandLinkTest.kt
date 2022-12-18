package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.mcwonderland.domain.command.CommandContext
import org.mcwonderland.domain.command.CommandContextStub
import org.mcwonderland.domain.command.CommandTestBase
import org.mcwonderland.domain.commands.CommandLink
import org.mcwonderland.domain.commands.CommandLinkHandle
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.features.AccountLinker
import kotlin.test.Test

class CommandLinkTest : CommandTestBase() {

    private val label = "link"
    private lateinit var accountLinker: AccountLinker
    private lateinit var handle: CommandLinkHandle<CommandContext>

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        handle = mockk(relaxed = true)
        command = CommandLink(label, accountLinker, handle)
    }

    @Test
    fun missingArguments() {
        executeWithNoArgs().also {
            verify { handle.missingArgId(context) }
        }
    }

    @Nested
    inner class Executed {

        private val targetId: String = "target_id"

        @Test
        fun linked_sendMessage() {
            every { accountLinker.link(sender, targetId) } returns sender

            executeCommand(targetId).also {
                verify { handle.linked(context, sender) }
            }
        }

        @Test
        fun testExceptionMappings() {
            assertException(AccountAlreadyLinkedException(linkedId = "mcId")) {
                handle.failAccountAlreadyLinked(
                    context,
                    it
                )
            }
            assertException(MCAccountNotFoundException(searchStr = "id")) { handle.failMcAccountNotFound(context, it) }
            assertException(MCAccountLinkedByOthersException(ign = "ign")) {
                handle.failMcAccountLinkedByOthers(
                    context,
                    it
                )
            }
        }

        private fun <T : Exception> assertException(exception: T, block: (T) -> Unit) {
            every { accountLinker.link(sender, targetId) } throws exception
            executeCommand(targetId)
            verify { block(exception) }
        }


    }
}
