package org.mcwonderland.domain.service

import io.mockk.every
import io.mockk.mockk
import io.mokulu.discord.oauth.DiscordOAuth
import io.mokulu.discord.oauth.model.TokensResponse
import org.jsoup.HttpStatusException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mcwonderland.access.DiscordApiCreator
import org.mcwonderland.access.DiscordAuthApiImpl
import org.mcwonderland.domain.DiscordAuthApi
import org.mcwonderland.domain.exceptions.InvalidOAuthException
import org.mcwonderland.domain.model.DiscordUser
import kotlin.test.assertEquals

class DiscordAuthApiImplTest {
    private lateinit var authApi: DiscordAuthApi
    private lateinit var discordOAuth: DiscordOAuth
    private lateinit var apiCreator: DiscordApiCreator

    private val accessToken = "accessToken"

    private val expectedUser = DiscordUser(
        id = "249768208326721537",
        email = "jerry@gmail.com",
        username = "jerry"
    )

    private val code = "code"

    @BeforeEach
    fun setUp() {
        discordOAuth = mockk(relaxed = true)
        apiCreator = mockk(relaxed = true)

        authApi = DiscordAuthApiImpl(discordOAuth, apiCreator)

        every { apiCreator.createDiscordApi(accessToken) } returns mockk(relaxed = true) {
            every { fetchUser() } returns mockk(relaxed = true) {
                every { id } returns expectedUser.id
                every { email } returns expectedUser.email
                every { username } returns expectedUser.username
            }
        }
    }

    @Test
    fun oAuthException_shouldRemap() {
        every { discordOAuth.getTokens(code) } throws HttpStatusException("error", 400, "url")

        assertThrows<InvalidOAuthException> {
            authApi.findUserByCode("code")
        }
    }

    @Test
    fun shouldExtractUser() {
        val token: TokensResponse = mockk(relaxed = true) { every { accessToken } returns this@DiscordAuthApiImplTest.accessToken }
        every { discordOAuth.getTokens(code) } returns token

        val user = authApi.findUserByCode(code)

        assertEquals(user, expectedUser)
    }

}