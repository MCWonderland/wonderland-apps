package org.mcwonderland.access

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mcwonderland.domain.fakes.ConfigStub
import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.repository.TeamRepository

internal class TeamRepositoryImplTest : MongoDBTest() {

    private lateinit var teamRepository: TeamRepository

    private val collection
        get() = getDB().getTeamCollection()

    @BeforeEach
    fun setup() {
        teamRepository = TeamRepositoryImpl(mongoClient, config)
    }

    @Test
    fun findUsersTeam() {
        val team = DBTeam(listOf("member"))
        collection.insertOne(team)

        assertEquals(team, teamRepository.findUsersTeam("member"))
    }

    @Test
    fun insertTeam() {
        val team = DBTeam(listOf("member"))
        teamRepository.insertTeam(team)

        assertEquals(team, collection.find().first())
    }
}