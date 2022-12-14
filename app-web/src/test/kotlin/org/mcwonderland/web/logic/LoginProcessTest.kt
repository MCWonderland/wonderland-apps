package org.mcwonderland.web.logic

import io.mockk.every
import io.mockk.mockk
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.UserStub
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.testdoubles.ConfigStub
import org.mcwonderland.domain.testdoubles.UserTokenServiceFake
import org.mcwonderland.web.Config
import org.mcwonderland.web.request.LoginRequest
import javax.inject.Inject
import javax.ws.rs.core.NewCookie

class LoginProcessTest {

    private lateinit var loginProcess: LoginProcess
    private lateinit var config: Config
    private lateinit var authService: AuthService

    private val userTokenService = UserTokenServiceFake()

    @BeforeEach
    fun setUp() {
        config = ConfigStub()
        authService = mockk()

        loginProcess = LoginProcess(
            authService = authService,
            userTokenService = userTokenService,
            config = config
        )
    }

    @Test
    fun shouldCallService() {
        val request = LoginRequest("code")
        val user = UserStub("id")

        every { authService.login(request.code) } returns user

        assertEquals(
            loginProcess.login(request),
            NewCookie(
                config.tokenCookieKey,
                userTokenService.encodeToken(user),
                "/",
                config.websiteDomain.replace("www", ""),
                null,
                -1,
                false,
                false,
            )
        )
    }

}