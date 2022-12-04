package org.mcwonderland.access

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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

    @Test
    fun findAll() {
        val team = DBTeam(listOf("member"))
        collection.insertOne(team)

        assertEquals(listOf(team), teamRepository.findAll())
    }

    @Test
    fun removeUserFromTeam() {
        val team = DBTeam(listOf("member"))
        collection.insertOne(team)

        assertEquals(DBTeam(), teamRepository.removeUserFromTeam("member"))
        assertEquals(null, teamRepository.findUsersTeam("member"))
    }
}