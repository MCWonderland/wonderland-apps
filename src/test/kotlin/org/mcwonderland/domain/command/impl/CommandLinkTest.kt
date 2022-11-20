package org.mcwonderland.domain.command.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.AccountLinker
import org.mcwonderland.domain.UserFinder
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.exception.InvalidArgumentException
import org.mcwonderland.domain.command.exception.MissingArgumentException
import java.util.UUID
import kotlin.test.Test

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker
    private lateinit var userFinder: UserFinder

    private val label = "link"
    private val commandSender = Dummies.createCommandSender()

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        userFinder = mockk(relaxed = true)
        command = CommandLink(label, accountLinker, userFinder)
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
        val foundedUser = Dummies.createUserDefault()

        every { userFinder.findOrCreate(commandSender.id) } returns foundedUser

        command.execute(commandSender, listOf(uuid))

        verify { accountLinker.link(foundedUser, uuid) }
    }
}
