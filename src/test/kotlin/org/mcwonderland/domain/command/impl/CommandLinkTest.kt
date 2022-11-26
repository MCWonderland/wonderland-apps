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
import org.mcwonderland.domain.features.AccountLinker
import org.mcwonderland.domain.features.UserFinder
import java.util.*
import kotlin.test.Test

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker
    private lateinit var userFinder: UserFinder
    private lateinit var messenger: Messenger
    private lateinit var messages: Messages

    private val label = "link"
    private val commandSender = Dummies.createCommandSender()

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        userFinder = mockk(relaxed = true)
        messenger = mockk(relaxed = true)
        messages = MessagesStub()

        command = CommandLink(label, accountLinker, userFinder, messenger, messages)
    }

    @Test
    fun missingArguments() {
        command.execute(commandSender, listOf())
        verify { messenger.sendMessage(messages.invalidArg("UUID")) }
    }

    @Test
    fun invalidUUID() {
        command.execute(commandSender, listOf("invalid_uuid"))
        verify { messenger.sendMessage(messages.invalidArg("UUID")) }
    }

    @Nested
    inner class Executed {

        private val uuid = UUID.randomUUID().toString()
        private val foundedUser = Dummies.createUserDefault()

        @Test
        fun linkFailed_sendMessages() {
            val message = "message"
            val exception = Exception(message)

            every { userFinder.findOrCreate(commandSender.id) } returns foundedUser
            every { accountLinker.link(foundedUser, uuid) } throws exception

            try {
                command.execute(commandSender, listOf(uuid))
            } finally {
                verify { messenger.sendMessage(message) }
            }
        }
    }
}
