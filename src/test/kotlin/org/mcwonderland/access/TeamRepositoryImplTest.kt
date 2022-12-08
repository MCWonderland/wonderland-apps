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

    private val team = DBTeam("id", listOf("member"))

    @BeforeEach
    fun setup() {
        teamRepository = TeamRepositoryImpl(mongoClient, config)
    }

    @Test
    fun findUsersTeam() {
        collection.insertOne(team)

        assertEquals(team, teamRepository.findUsersTeam("member"))
    }

    @Test
    fun insertTeam() {
        teamRepository.insertTeam(team)

        assertEquals(team, collection.find().first())
    }

    @Test
    fun findAll() {
        collection.insertOne(team)

        assertEquals(listOf(team), teamRepository.findAll())
    }

    @Test
    fun removeUserFromTeam() {
        collection.insertOne(team)

        assertEquals(team.apply { members = emptyList() }, teamRepository.removeUserFromTeam("member"))
        assertEquals(null, teamRepository.findUsersTeam("member"))
    }

    @Test
    fun addUserToTeam() {
        val expected = team.apply { members = listOf("member", "newMember") }

        collection.insertOne(team)

        assertEquals(expected, teamRepository.addUserToTeam("newMember", team.id))
    }

    @Test
    fun deleteTeam() {
        collection.insertOne(team)

        assertEquals(team, teamRepository.deleteTeam(team.id))
        assertEquals(null, teamRepository.findUsersTeam("member"))
    }
}