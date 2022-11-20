package org.mcwonderland.domain.command.impl

import io.mockk.InternalPlatformDsl.toStr
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.command.Command
import org.mcwonderland.domain.command.exception.InvalidArgumentException
import org.mcwonderland.domain.command.exception.MissingArgumentException
import org.mcwonderland.domain.model.User
import java.util.UUID
import kotlin.test.Test

class CommandLinkTest {
    private lateinit var command: Command
    private lateinit var accountLinker: AccountLinker

    private val label = "link"
    private val user = User("user_id")

    @BeforeEach
    fun setUp() {
        accountLinker = mockk(relaxed = true)
        command = CommandLink(label, accountLinker)
    }

    @Test
    fun missingArguments() {
        assertThrows<MissingArgumentException>("UUID") { command.execute(user, listOf()) }
    }

    @Test
    fun invalidUUID() {
        assertThrows<InvalidArgumentException>("UUID") { command.execute(user, listOf("invalid_uuid")) }
    }

    @Test
    fun shouldCallAccountLinker() {
        val uuid = UUID.randomUUID().toString()

        command.execute(user, listOf(uuid))

        verify { accountLinker.link(user, uuid) }
    }
}
