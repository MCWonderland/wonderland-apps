package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.MessageSender
import org.mcwonderland.domain.UserFinder
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.exception.InvalidArgumentException
import org.mcwonderland.domain.command.exception.MissingArgumentException
import java.lang.Exception
import java.util.UUID
import kotlin.test.Test

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker
    private lateinit var userFinder: UserFinder
    private lateinit var messageSender: MessageSender

    private val label = "link"
    private val commandSender = Dummies.createCommandSender()

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        userFinder = mockk(relaxed = true)
        messageSender = mockk(relaxed = true)

        command = CommandLink(label, accountLinker, userFinder, messageSender)
    }

    @Test
    fun missingArguments() {
        assertThrows<MissingArgumentException>("UUID") { command.execute(commandSender, listOf()) }
    }

    @Test
    fun invalidUUID() {
        assertThrows<InvalidArgumentException>("UUID") { command.execute(commandSender, listOf("invalid_uuid")) }
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
                verify { messageSender.sendMessage(message) }
            }
        }
    }
}
