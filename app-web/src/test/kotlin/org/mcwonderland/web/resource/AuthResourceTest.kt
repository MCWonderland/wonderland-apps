package org.mcwonderland.web.resource

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.domain.service.UserTokenService
import org.mcwonderland.domain.testdoubles.UserTokenServiceFake
import org.mcwonderland.web.Config
import org.mcwonderland.web.request.LoginRequest
import javax.inject.Inject
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie

@QuarkusTest
class AuthResourceTest {

    @InjectMock(relaxed = true)
    private lateinit var authService: AuthService

    @Inject
    lateinit var config: Config

    private val userTokenService: UserTokenService = UserTokenServiceFake()

    @BeforeEach
    fun setup() {
        QuarkusMock.installMockForType(authService, AuthService::class.java)
        QuarkusMock.installMockForType(userTokenService, UserTokenService::class.java)
    }


    @Nested
    inner class Login {

        @Test
        fun withoutBody_should400() {
            jsonReq()
                .sendRequest()
                .statusCode(400)
        }

        @Test
        fun shouldCallService() {
            val request = LoginRequest("code")
            val user = User("id")

            every { authService.login(request.code) } returns user

            jsonReq()
                .body(request)
                .sendRequest()
                .statusCode(200)
                .header(
                    "Set-Cookie",
                    NewCookie(
                        config.tokenCookieKey,
                        userTokenService.encodeToken(user),
                        "/",
                        config.websiteDomain.replace("www", ""),
                        null,
                        -1,
                        false,
                        false,
                    ).toString().plus(";SameSite=Strict")
                )
        }

        private fun jsonReq(): RequestSpecification {
            return given().contentType(MediaType.APPLICATION_JSON)
        }

        private fun RequestSpecification.sendRequest(): ValidatableResponse {
            return this.`when`().post("/auth/login").then()
        }
    }

}