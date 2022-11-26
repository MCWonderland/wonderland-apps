package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.mcwonderland.domain.Messenger
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.MessengerFake
import org.mcwonderland.domain.fakes.UserFinderStub
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.features.UserFinder
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker
    private lateinit var userFinder: UserFinder
    private lateinit var messenger: MessengerFake
    private lateinit var messages: Messages

    private val label = "link"
    private val commandSender = Dummies.createCommandSender()
    private val foundedUser = Dummies.createUserDefault()

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        userFinder = UserFinderStub(foundedUser)
        messenger = MessengerFake()
        messages = MessagesStub()

        command = CommandLink(label, accountLinker, userFinder, messenger, messages)
    }

    @Test
    fun missingArguments() {
        command.execute(commandSender, listOf())
        assertEquals(messages.invalidArg("mcIgn"), messenger.lastMessage)
    }

    @Nested
    inner class Executed {

        private val targetId: String = "target_id"

        @Test
        fun linked_sendMessage() {
            command.execute(commandSender, listOf(targetId))

            every { accountLinker.link(foundedUser, targetId) } returns foundedUser

            assertEquals(messages.linked(foundedUser), messenger.lastMessage)
        }
    }
}
