package org.mcwonderland.domain.command.impl

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.Dummies
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.exception.InvalidArgumentException
import org.mcwonderland.domain.command.exception.MissingArgumentException
import org.mcwonderland.domain.model.CommandSender
import java.util.UUID
import kotlin.test.Test

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker

    private val label = "link"
    private val commandSender = Dummies.createCommandSender()

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        command = CommandLink(label, accountLinker)
    }

    @Test
    fun missingArguments() {
        assertThrows<MissingArgumentException>("UUID") { command.execute(commandSender, listOf()) }
    }

    @Test
    fun invalidUUID() {
        assertThrows<InvalidArgumentException>("UUID") { command.execute(commandSender, listOf("invalid_uuid")) }
    }

    @Test
    fun shouldCallAccountLinker() {
        val uuid = UUID.randomUUID().toString()

        command.execute(commandSender, listOf(uuid))

        verify { accountLinker.link(commandSender, uuid) }
    }
}
