package org.mcwonderland.access

import com.auth0.jwt.algorithms.Algorithm
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import kotlin.test.assertEquals
import kotlin.test.assertNull


class UserTokenServiceImplTest {

    private lateinit var userTokenService: UserTokenServiceImpl

    private val user = Dummies.createUserFullFilled()

    private val settings = JwtConfig(
        issuer = "test",
        algorithm = Algorithm.HMAC256("secret")
    )


    @BeforeEach
    fun setUp() {
        userTokenService = UserTokenServiceImpl(settings)
    }

    @Test
    fun shouldEncodeAndDecode() {
        val token = userTokenService.encodeToken(user)
        assertEquals(user, userTokenService.decodeToken(token))
    }

    @Test
    fun decodeModified_shouldReturnNull() {
        /**
         * a token that modified
         *
         * {
         *   "mcId": "123",
         *   "discordId": "123",
         *   "id": "123456", (original: 123)
         *   "isAdmin": false
         * }
         */
        assertNull(userTokenService.decodeToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtY0lkIjoiMTIzIiwiZGlzY29yZElkIjoiMTIzIiwiaWQiOiIxMjM0NTYiLCJpc0FkbWluIjpmYWxzZX0.2mBdR8b-7M1jM-JIjb6S7vWEPZSKZtqFgsu6w4Z_Xro"))
    }

    @Test
    fun withDifferentIssuer_shouldReturnNull() {
        val svcWithDiffIssuer = UserTokenServiceImpl(settings.copy(issuer = "diff"))

        val tokenDiffIssuer = svcWithDiffIssuer.encodeToken(user)

        assertNull(userTokenService.decodeToken(tokenDiffIssuer))
    }
}