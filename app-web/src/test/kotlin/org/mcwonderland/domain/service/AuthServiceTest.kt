package org.mcwonderland.domain.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.UserFinderFake
import org.mcwonderland.domain.fakes.UserRepositoryFake
import org.mcwonderland.domain.features.UserFinder
import org.mcwonderland.domain.model.DiscordUser
import org.mcwonderland.domain.repository.UserRepository
import kotlin.test.assertEquals

class AuthServiceTest {

    private lateinit var authService: AuthService
    private lateinit var discordOAuth: DiscordOAuth
    private lateinit var userFinder: UserFinder

    @BeforeEach
    fun setup() {
        discordOAuth = mockk(relaxed = true)
        userFinder = UserFinderFake()
        authService = AuthService(discordOAuth, userFinder)
    }


    @Test
    fun login_shouldFindOrCreateUser() {
        val discordUser = DiscordUser(
            id = "273702645280276481",
            email = "leegod@gmail.com",
            username = "LeeGod"
        )

        every { discordOAuth.findUserByCode("code") } returns discordUser

        val user = authService.login("code")

        assertEquals(user, userFinder.find(discordUser.id))
    }


}