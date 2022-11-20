package org.mcwonderland.discord

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.AccountFinder
import org.mcwonderland.domain.Dummies
import org.mcwonderland.domain.UserCreator
import org.mcwonderland.domain.exception.AccountAlreadyOwnedException
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.exception.AlreadyLinkedException
import org.mcwonderland.domain.repository.UserRepository
import java.util.UUID

internal class DiscordMcAccountLinkerTest {

    private lateinit var linker: DiscordMcAccountLinker
    private lateinit var accountFinder: AccountFinder
    private lateinit var userRepository: UserRepository
    private lateinit var userCreator: UserCreator

    private val sender = Dummies.createCommandSender()
    private val target: UUID = UUID.randomUUID()

    @BeforeEach
    fun setUp() {
        userRepository = mockk(relaxed = true)
        accountFinder = mockk(relaxed = true)
        userCreator = UserCreator()
        linker = DiscordMcAccountLinker(accountFinder, userCreator, userRepository)
    }


    @Test
    fun alreadyLinked_shouldThrowException() {
        val userMcLinked = Dummies.createUserFullFilled().apply {
            this.mcId = UUID.randomUUID().toString()
        }

        every { userRepository.findUserByDiscordId(sender.id) } returns userMcLinked

        assertThrows<AlreadyLinkedException> {
            linker.link(sender, target.toString())
        }
    }

    @Test
    fun accountNotExist_shouldThrowException() {
        every { accountFinder.isAccountExist(target.toString()) } returns false

        assertThrows<AccountNotExistException> { linker.link(sender, target.toString()) }
    }

    @Test
    fun targetAccountAlreadyLinked_shouldThrowException() {
        val uuid = UUID.randomUUID()

        every { accountFinder.isAccountExist(uuid.toString()) } returns true
        every { userRepository.findUserByMcId(uuid.toString()) } returns Dummies.createUserFullFilled()

        assertThrows<AccountAlreadyOwnedException> { linker.link(sender, uuid.toString()) }
    }

    @Test
    fun shouldLink() {
        val uuid = UUID.randomUUID()
        val user = Dummies.createUserDefault()

        every { accountFinder.isAccountExist(uuid.toString()) } returns true
        every { userRepository.findUserByDiscordId(sender.id) } returns user
        every { userRepository.findUserByMcId(uuid.toString()) } returns null

        linker.link(sender, uuid.toString())

        verify { userRepository.updateMcId(user.id, uuid.toString()) }
    }

}