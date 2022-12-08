package org.mcwonderland.domain.fakes

import org.mcwonderland.domain.model.DBTeam
import org.mcwonderland.domain.model.User
import org.mcwonderland.domain.repository.TeamRepository
import java.util.UUID

class TeamRepositoryFake : TeamRepository {

    private val teams = mutableListOf<DBTeam>()

    override fun findUsersTeam(userId: String): DBTeam? {
        return teams.find { it.members.contains(userId) }
    }

    override fun insertTeam(team: DBTeam) {
        teams.add(team)
    }

    override fun findAll(): List<DBTeam> {
        return teams.toList()
    }

    override fun removeUserFromTeam(id: String): DBTeam? {
        val team = teams.find { it.members.contains(id) } ?: return null
        teams.remove(team)
        return team
    }

    override fun addUserToTeam(userId: String, teamId: String): DBTeam? {
        val team = teams.find { it.id == teamId } ?: return null
        teams.add(team.copy(members = team.members + userId))
        return team
    }

    fun createTeamWithUsers(vararg users: User): DBTeam {
        return createEmptyTeam(UUID.randomUUID().toString()).apply { members = users.map { it.id } }
    }

    fun createEmptyTeam(id: String): DBTeam {
        val team = DBTeam(id = id, emptyList())
        teams.add(team)

        return team
    }

}