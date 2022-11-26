package org.mcwonderland.discord

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.assertRuntimeError
import org.mcwonderland.domain.config.Messages
import org.mcwonderland.domain.config.MessagesStub
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.MojangAccountFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.model.User
import java.util.*
import kotlin.test.assertEquals

internal class DiscordMcAccountLinkerTest {

    private lateinit var linker: DiscordMcAccountLinker
    private lateinit var mojangAccount: MojangAccountFake
    private lateinit var userRepository: UserRepositoryFake
    private lateinit var messages: Messages

    private val sender = Dummies.createUserDefault()
    private val target: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        messages = MessagesStub()
        userRepository = UserRepositoryFake()
        mojangAccount = MojangAccountFake()
        linker = DiscordMcAccountLinker(mojangAccount, userRepository, messages)
    }


    @Test
    fun alreadyLinked_shouldThrowException() {
        sender.mcId = "123"

        assertRuntimeError(messages.accountAlreadyLinked()) {
            linker.link(sender, target.toString())
        }
    }

    @Test
    fun accountNotExist_shouldThrowException() {
        assertRuntimeError(messages.accountNotFound()) { linker.link(sender, target.toString()) }
    }

    @Test
    fun targetAccountAlreadyLinked_shouldThrowException() {
        val uuid = UUID.randomUUID()

        mojangAccount.addAccount(uuid.toString())
        userRepository.addUser(User(mcId = uuid.toString()))

        assertRuntimeError(messages.targetAccountAlreadyLink()){ linker.link(sender, uuid.toString()) }
    }

    @Test
    fun shouldLink() {
        val uuid = UUID.randomUUID()

        mojangAccount.addAccount(uuid.toString())
        userRepository.addUser(sender)

        linker.link(sender, uuid.toString())

        assertEquals(uuid.toString(), sender.mcId)
    }

}