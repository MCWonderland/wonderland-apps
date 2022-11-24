package org.mcwonderland.minecraft

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang
import java.util.UUID

internal class MojangAccountImplTest {

    private lateinit var mojangAccount: MojangAccount
    private lateinit var mojang: Mojang


    @BeforeEach
    fun setUp() {
        mojang = mockk()
        mojangAccount = MojangAccountImpl(mojang)
    }


    @Nested
    inner class IsAccountExist {
        @Test
        fun apiResponseNull_shouldReturnsFalse() {
            val uuid = UUID.randomUUID()

            every { mojang.getPlayerProfile(uuid.toString()) } returns null

            assertFalse(mojangAccount.isAccountExist(uuid.toString()))
        }

        @Test
        fun apiThrowsException_shouldReturnsFalse() {
            val uuid = UUID.randomUUID()

            every { mojang.getPlayerProfile(uuid.toString()) } throws Exception()

            assertFalse(mojangAccount.isAccountExist(uuid.toString()))
        }

    }
}