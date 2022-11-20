package org.mcwonderland.discord

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.MojangAccount
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
import org.mcwonderland.domain.repository.UserRepository
import java.util.UUID

internal class DiscordMcAccountLinkerTest {

    private lateinit var linker: DiscordMcAccountLinker
    private lateinit var mojangAccount: MojangAccount
    private lateinit var userRepository: UserRepository

    private val sender = Dummies.createUserDefault()
    private val target: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        mojangAccount = mockk(relaxed = true)
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
        every { mojangAccount.isAccountExist(target.toString()) } returns false

        assertThrows<AccountNotExistException> { linker.link(sender, target.toString()) }
    }

    @Test
    fun targetAccountAlreadyLinked_shouldThrowException() {
        val uuid = UUID.randomUUID()

        every { mojangAccount.isAccountExist(uuid.toString()) } returns true
        every { userRepository.findUserByMcId(uuid.toString()) } returns Dummies.createUserFullFilled()

        assertThrows<AccountAlreadyOwnedException> { linker.link(sender, uuid.toString()) }
    }

    @Test
    fun shouldLink() {
        val uuid = UUID.randomUUID()
        val user = Dummies.createUserDefault()

        every { mojangAccount.isAccountExist(uuid.toString()) } returns true
        every { userRepository.findUserByMcId(uuid.toString()) } returns null

        linker.link(sender, uuid.toString())

        verify { userRepository.updateMcId(user.id, uuid.toString()) }
    }

}