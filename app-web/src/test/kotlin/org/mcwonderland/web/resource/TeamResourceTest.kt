package org.mcwonderland.web.resource

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.verify
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.Mock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mcwonderland.domain.features.TeamService
import org.mcwonderland.domain.model.Team

@QuarkusTest
class TeamResourceTest : ResourceTest() {

    @InjectMock
    private lateinit var teamService: TeamService


    @Nested
    inner class ListTeams {

        @Test
        fun shouldCallService() {
            val excepted = Team(id = "teamId")

            every { teamService.listTeams() } returns listOf(excepted)

            given()
                .`when`()
                .get("/teams")
                .then()
                .assertOk()
                .body("teams[0].id", equalTo(excepted.id))
        }
    }

}