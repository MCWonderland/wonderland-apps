package org.mcwonderland.web.resource

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.service.AuthService
import org.mcwonderland.web.request.LoginRequest

@QuarkusTest
class AuthResourceTest {

    @InjectMock(relaxed = true)
    private lateinit var authService: AuthService

    @Nested
    inner class Login {

        @Test
        fun withoutBody_should400() {
            given()
                .sendRequest()
                .statusCode(400)
        }

        @Test
        fun shouldCallService(){
            val request = LoginRequest("code")
            val user = User("id")

            every { authService.login(request.code) } returns user

            given()
                .body(request)
                .sendRequest()
                .statusCode(200)
        }

        private fun RequestSpecification.sendRequest(): ValidatableResponse {
            return this.`when`().post("/auth/login").then()
        }
    }

}