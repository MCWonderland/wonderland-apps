package org.mcwonderland.domain.features

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.fakes.UserRepositoryFake
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class UserFinderDiscordTest {

    private lateinit var userFinder: UserFinder
    private lateinit var userRepository: UserRepositoryFake

    @BeforeEach
    fun setup() {
        userRepository = UserRepositoryFake()
        userFinder = UserFinderDiscord(userRepository)
    }

    @Test
    fun userWithDiscordIdNotExist_shouldNull() {
        assertNull(userFinder.find("123"))
    }

    @Test
    fun userExists_shouldReturn() {
        val expected = Dummies.createUserEmpty().apply { discordProfile.id = "hello" }
        userRepository.addUser(expected)

        assertEquals(expected, userFinder.find("hello"))
    }

}