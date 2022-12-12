package org.mcwonderland.web.resource

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.fakes.Dummies
import org.mcwonderland.domain.model.User

@QuarkusTest
class RegistrationResourceTest : ResourceTest() {

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

            given()
                .`when`()
                .withToken(user)
                .get("/registrations")
                .then()
                .assertOk()
        }

    }

}

