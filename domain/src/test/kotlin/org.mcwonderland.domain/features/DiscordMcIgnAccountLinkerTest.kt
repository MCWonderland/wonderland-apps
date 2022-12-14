package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.exceptions.AccountAlreadyLinkedException
import org.mcwonderland.domain.exceptions.MCAccountLinkedByOthersException
import org.mcwonderland.domain.exceptions.MCAccountNotFoundException
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.MojangAccountFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import kotlin.test.assertEquals

internal class DiscordMcIgnAccountLinkerTest {

    private lateinit var linker: DiscordMcIgnAccountLinker
    private lateinit var mojangAccount: MojangAccountFake
    private lateinit var userRepository: UserRepositoryFake

    private val sender = Dummies.createUserEmpty()

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryFake()
        mojangAccount = MojangAccountFake()
        linker = DiscordMcIgnAccountLinker(mojangAccount, userRepository)
    }


    @Nested
    inner class LinkAccount {
        @Test
        fun alreadyLinked_shouldThrowException() {
            sender.mcProfile.uuid = "123"

            assertThrows<AccountAlreadyLinkedException> {
                linker.link(sender, "target")
            }.also {
                assertEquals(it.linkedId, sender.mcProfile.uuid)
            }
        }

        @Test
        fun accountNotExist_shouldThrowException() {
            assertThrows<MCAccountNotFoundException> {
                linker.link(sender, "target")
            }.also {
                assertEquals(it.searchStr, "target")
            }
        }

        @Test
        fun targetAccountAlreadyLinked_shouldThrowException() {
            val account = mojangAccount.addRandomAccount()

            userRepository.addUser(Dummies.createUserEmpty().apply { mcProfile.uuid = account.uuid.toString() })

            assertThrows<MCAccountLinkedByOthersException> {
                linker.link(sender, account.name)
            }.also {
                assertEquals(it.ign, account.name)
            }
        }

        @Test
        fun shouldLink() {
            val account = mojangAccount.addRandomAccount()

            userRepository.addUser(sender)

            linker.link(sender, account.name)

            assertEquals(account.uuid.toString(), sender.mcProfile.uuid)
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
            sender.mcProfile.uuid = "123"
            assertEquals(true, linker.isLinked(sender))
        }

    }

}