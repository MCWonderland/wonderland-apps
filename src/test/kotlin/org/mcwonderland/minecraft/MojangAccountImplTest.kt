package org.mcwonderland.minecraft

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.MojangAccount
import org.shanerx.mojang.Mojang
import java.util.*

internal class MojangAccountImplTest {

    private lateinit var mojangAccount: MojangAccount
    private lateinit var mojang: Mojang


    @BeforeEach
    fun setUp() {
        mojang = mockk()
        mojangAccount = MojangAccountImpl(mojang)
    }


    @Nested
    inner class GetUUIDByName {
        private val name = "user_ign"

        @Test
        fun apiResponseNull_shouldReturnsFalse() {
            every { mojang.getUUIDOfUsername(name) } returns null

            assertNull(mojangAccount.getUUIDByName(name))
        }

        @Test
        fun apiThrowsException_shouldNull(){
            every { mojang.getUUIDOfUsername(name) } throws Exception()

            assertNull(mojangAccount.getUUIDByName(name))
        }

        @Test
        fun shouldParseUuidThatWithoutDashes() {
            val uuid = UUID.randomUUID()

            every { mojang.getUUIDOfUsername(name) } returns uuid.toString().replace("-", "")

            assertEquals(uuid, mojangAccount.getUUIDByName(name))
        }
    }

    @Nested
    inner class GetNameByUUID {
        @Test
        fun apiResponseNull_shouldReturnNull() {
            val uuid = UUID.randomUUID()
            every { mojang.getPlayerProfile(uuid.toString()) } returns null

            assertNull(mojangAccount.getNameByUUID(uuid.toString()))
        }

        @Test
        fun apiThrowsException_shouldReturnNull(){
            val uuid = UUID.randomUUID()
            every { mojang.getPlayerProfile(uuid.toString()) } throws Exception()

            assertNull(mojangAccount.getNameByUUID(uuid.toString()))
        }

        @Test
        fun shouldReturnName() {
            val uuid = UUID.randomUUID()
            val name = "test"

            every { mojang.getPlayerProfile(uuid.toString()) } returns mockk(relaxed = true) {
                every { username } returns name
            }

            assertEquals(name, mojangAccount.getNameByUUID(uuid.toString()))
        }
    }
}