package org.mcwonderland.web.resource

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.features.RegistrationService
import org.mcwonderland.domain.model.User

@QuarkusTest
class RegistrationResourceTest : ResourceTest() {

    @InjectMock
    lateinit var registrationService: RegistrationService

    @Nested
    inner class ListRegistrations {

        @Test
        fun withoutAuth_should401() {
            given()
                .`when`()
                .get("/registrations")
                .then()
                .assertUnauthorized()
        }

        @Test
        fun shouldCallService() {
            val user = Dummies.createUserFullFilled()

            every { registrationService.listRegistrations() } returns listOf(user)

            given()
                .`when`()
                .withToken(user)
                .get("/registrations")
                .then()
                .assertOk()
                .body("users[0].id", equalTo(user.id))
        }

    }

}

