package org.mcwonderland.discord

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.discord.impl.UserFinderDiscord
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.features.UserFinder
import java.util.*
import kotlin.test.Test

internal class UserFinderDiscordTest {

    private lateinit var userFinder: UserFinder
    private lateinit var userRepository: UserRepositoryFake

    private val fixedUuid = UUID.randomUUID()

    @BeforeEach
    fun setup() {
        userRepository = UserRepositoryFake()
        userFinder = UserFinderDiscord(userRepository)
    }

    @Test
    fun userNotExistInDB_shouldCreate() {
        mockUuid()

        val discordId = "123456789"
        val user = userFinder.findOrCreate(discordId)

        assertEquals(discordId, user.discordId)
        assertEquals(fixedUuid.toString(), user.id)
        assertEquals(userRepository.findUserByDiscordId(discordId), user)
    }

    private fun mockUuid() {
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns fixedUuid
    }
}