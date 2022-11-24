package org.mcwonderland.domain

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions.*
import org.mcwonderland.domain.fakes.UserRepositoryFake
import java.util.UUID
import kotlin.test.Test

internal class UserFinderByDiscordIdTest {

    @Test
    fun userNotExistInDB_shouldCreate() {
        val fixedUuid = UUID.randomUUID()
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns fixedUuid

        val discordId = "123456789"

        val db = UserRepositoryFake()
        val userFinder = UserFinderByDiscordId(db)

        val user = userFinder.findOrCreate(discordId)

        assertEquals(discordId, user.discordId)
        assertEquals(fixedUuid.toString(), user.id)
        assertEquals(db.findUserByDiscordId(discordId), user)
    }

}