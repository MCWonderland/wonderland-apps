package org.mcwonderland.discord

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
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

internal class DiscordMcIgnAccountLinkerTest {

    private lateinit var linker: DiscordMcIgnAccountLinker
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
        linker = DiscordMcIgnAccountLinker(mojangAccount, userRepository, messages)
    }


    @Nested
    inner class LinkAccount {
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
            val account = mojangAccount.addRandomAccount()

            userRepository.addUser(User(mcId = account.uuid.toString()))

            assertRuntimeError(messages.targetAccountAlreadyLink()) { linker.link(sender, account.name) }
        }

        @Test
        fun shouldLink() {
            val account = mojangAccount.addRandomAccount()

            userRepository.addUser(sender)

            linker.link(sender, account.name)

            assertEquals(account.uuid.toString(), sender.mcId)
        }
    }

    @Nested
    inner class IsLinked {
        @Test
        fun withoutMcId_shouldFalse() {
            assertEquals(false, linker.isLinked(sender))
        }

        @Test
        fun shouldTrueIfMcIdExists() {
            sender.mcId = "123"
            assertEquals(true, linker.isLinked(sender))
        }

    }
}