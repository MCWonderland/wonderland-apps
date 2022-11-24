package org.mcwonderland.discord

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
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

    private val sender = Dummies.createUserDefault()
    private val target: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        userRepository = UserRepositoryFake()
        mojangAccount = MojangAccountFake()
        linker = DiscordMcAccountLinker(mojangAccount, userRepository)
    }


    @Test
    fun alreadyLinked_shouldThrowException() {
        sender.mcId = "123"

        assertThrows<AlreadyLinkedException> {
            linker.link(sender, target.toString())
        }
    }

    @Test
    fun accountNotExist_shouldThrowException() {
        assertThrows<AccountNotExistException> { linker.link(sender, target.toString()) }
    }

    @Test
    fun targetAccountAlreadyLinked_shouldThrowException() {
        val uuid = UUID.randomUUID()

        mojangAccount.addAccount(uuid.toString())
        userRepository.addUser(User(mcId = uuid.toString()))

        assertThrows<AccountAlreadyOwnedException> { linker.link(sender, uuid.toString()) }
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