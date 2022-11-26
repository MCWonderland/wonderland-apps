package org.mcwonderland.access

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.ConfigStub
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.repository.TeamRepository

internal class TeamRepositoryImplTest : MongoDBTest() {

    private lateinit var teamRepository: TeamRepository

    @BeforeEach
    fun setup() {
        teamRepository = TeamRepositoryImpl(mongoClient, config)
    }

    @Test
    fun findUsersTeam() {
        val team = DBTeam(listOf("member"))
        getDB().getTeamCollection().insertOne(team)

        assertEquals(team, teamRepository.findUsersTeam("member"))
    }

    @Test
    fun insertTeam() {
    }
}