package org.mcwonderland.discord

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.domain.AccountFinder
import org.mcwonderland.domain.Dummies
import org.mcwonderland.domain.exception.AccountNotExistException
import org.mcwonderland.domain.model.CommandSender
import java.util.UUID

internal class DiscordMCAccountLinkerTest {

    private lateinit var linker: DiscordMCAccountLinker
    private lateinit var accountFinder: AccountFinder

    private val sender = Dummies.createCommandSender()

    @BeforeEach
    fun setUp() {
        accountFinder = mockk(relaxed = true)
        linker = DiscordMCAccountLinker()
    }


    @Test
    fun mcAccountNotExist_shouldThrowAccountNotExistException() {
        val mcUUID = UUID.randomUUID()

        every { accountFinder.isAccountExist(mcUUID.toString()) } returns false

        assertThrows<AccountNotExistException> { linker.link(sender, mcUUID.toString()) }
    }

}